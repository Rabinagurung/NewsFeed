package com.example.newswave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newswave.databinding.FragmentSavedBinding
import com.example.newswave.presentation.adpater.NewsAdapter
import com.example.newswave.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class SavedFragment : Fragment() {

    private lateinit var fragmentSavedBinding:FragmentSavedBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter:NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSavedBinding = FragmentSavedBinding.bind(view)

        newsViewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("selected_article", article)
            }

            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle
            )
        }

        initRecyclerView()

        //observe the live data here
        newsViewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }

        //here we have two parameters for drag directions and for swipe directions
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //we need touched list item's position of the list
                //using that position we get article instance from the list
              val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                newsViewModel.deleteArticles(article)
                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        newsViewModel.saveArticle(article)
                    }
                    show()
                }


            }


        }
        //We need to attach the ItemTouchHelperCallback to the recycler view

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(fragmentSavedBinding.savedRV)
        }

    }

    private fun initRecyclerView(){

            //assign the instances for RV's adapter
            fragmentSavedBinding.savedRV.apply {
                adapter = newsAdapter // use the adapter from MA
                layoutManager = LinearLayoutManager(activity) // LiLinearLayoutManager instance created
            }

    }

}


//npx expo install firebase
//npx expo customize metro.config.js
//config.resolver.sourceExts.push('cjs') : create conn to create firebase sdk and database