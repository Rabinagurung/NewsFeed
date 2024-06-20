package com.example.newswave

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.newswave.data.model.Article
import com.example.newswave.databinding.FragmentInfoBinding
import com.example.newswave.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class InfoFragment : Fragment() {

    private lateinit var fragmentInfoBinding: FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel
    private val args: InfoFragmentArgs by navArgs()
    private lateinit var article: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentInfoBinding = FragmentInfoBinding.bind(view)

        article = args.selectedArticle

        viewModel = (activity as MainActivity).viewModel

        webViewInfo()
        observeSaveArticlesStatus()
        setupSaveButtonClickListener()
    }

    private fun webViewInfo(){
        fragmentInfoBinding.webViewInfo.apply {
            webViewClient = WebViewClient()
            article.url?.let{ url ->
                loadUrl(url)

            }
        }

    }
    private fun observeSaveArticlesStatus(){
        viewModel.saveArticleStatus.observe(viewLifecycleOwner) { status ->
            view?.let { Snackbar.make(it, status, Snackbar.LENGTH_LONG).show() }
        }
    }

    private  fun  setupSaveButtonClickListener(){
        fragmentInfoBinding.saveFloatingBtn.setOnClickListener(){
            viewModel.saveArticle(article)
        }
    }

}


/**
 D4 old

 g. We will write codes to get the InfoFragmentArgs instance using NavArgs().
 */

/***
E. Floating button added in info fragment xml file.
Setting onClick for that floating button: We need to execute the saveNews fun of NewsVM.
So, newsViewModel instance is already there in MA, where it is initialized with
ModelViewProvider and factory is also added.

Our concept is if we want to send the data from VM to fragment, then VM should be initialized
in MA then from there fragments can use functions of VM.

Here:  we just added the newsVM instance in InfoFragment using newModel instance present
in VM where we first initialized it.

 IMP: Inside the onCliCk of saveFloatingBtn:
    There is article instance we got from navigation arguments sent from NewsFragment using bundle.
    val article = args.selectedArticle.
    Finally write codes to display success message on the snack bar.
    Room allows us to get the inserted row id as a return value.
    We could have used for the verification before displaying the success message.
    Try it by yourself as a coding challenge.



 */