package com.example.newswave

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newswave.data.util.Resource

import com.example.newswave.databinding.FragmentNewsBinding
import com.example.newswave.presentation.adpater.NewsAdapter
import com.example.newswave.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var fragmentNewsBinding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var isScrolling: Boolean = false
    private var isLoading:Boolean = false
    private var isLastPage:Boolean = false
    private var country = "us"
    private var page = 1
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        newsAdapter.setOnItemClickListener {article ->
            val bundle = Bundle().apply {
                putSerializable("selected_article", article)
            }

            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle
            )
        }
        initRecyclerView()
        viewNewsList()
        setSearchView()

    }

    private fun viewNewsList(){
        viewModel.getNewsHeadline(country, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        Log.i("MYTAG", "came here ${it.articles.toList().size}")
                        newsAdapter.differ.submitList(it.articles.toList())
                        pages = if(it.totalResults%20 == 0) {
                            it.totalResults/20

                        } else {
                            it.totalResults/20 + 1
                        }

                        isLastPage = page == pages

                    }

                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }

                }

                is Resource.Loading -> {
                    showProgress()

                }


            }
        }
    }


    private fun initRecyclerView(){
        //newsAdapter = NewsAdapter()
        fragmentNewsBinding.newsRV.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }

    }

    private fun showProgress(){
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE

    }

    private fun hideProgressBar(){
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.newsRV.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()


            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList

            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if(shouldPaginate){
                page++
                viewModel.getNewsHeadline(country, page)
                isScrolling = false
            }
        }
    }

    private fun setSearchView(){
        fragmentNewsBinding.newsSV.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchNews("us", query.toString(), page )
                    viewSearchedNews()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                   MainScope().launch {
                       delay(2000)
                       viewModel.searchNews("us", newText.toString(), page)
                       viewSearchedNews()
                   }
                    return false
                }

            })


        fragmentNewsBinding.newsSV.setOnCloseListener {
            initRecyclerView()
            viewNewsList()
            false
        }


    }

    fun viewSearchedNews(){
        viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is com.example.newswave.data.util.Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {

                        newsAdapter.differ.submitList(it.articles.toList())
                        pages = if(it.totalResults%20 == 0) {
                            it.totalResults/20

                        } else {
                            it.totalResults/20 + 1
                        }

                        isLastPage = page == pages

                    }

                }

                is com.example.newswave.data.util.Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }
                }

                is com.example.newswave.data.util.Resource.Loading -> {
                    showProgress()
                }
            }
        }
    }
}


/***
 B.1

All we need to do is observe the searchedNews mutable live data present in NewsViewModel.
i. Lets create a function for that.

    fun ViewSearchedNews(
        newsViewModel.searchNews() ?? Create a fun to get the search query.
    )

 ii. In order to create that fun to get the search query, we need to implement
    setOnQueryListener of the search view.

    a. override fun onQueryTextSubmit: It will be invoked when the user type the search query
    and tap on the enter button of the keyboard. So, we need to write the codes to invoke
    the VM's searchNews() passing the typed searched query.
    b. After that we just need to call viewSearchedViews() fun we created to display the observed
    results on the RV.
    c.This onQueryTextChange fun will be invoked for each text change in the search
    view. Everytime we just type a text or remove a text on the search view, things we write
    inside this fun will execute.
    This is unnecessary waste of resources.
    So, we ned to give some time to user to write some meaningful search query.
    I am going to be delay the execution by 2 seconds using delay function of the coroutine.

    We can use CoroutineScope.launch here.
    But instead, I am going to use newly introduced MainScope.
    MainScope is a coroutine launcher specially created for User Interface components.
    MainScope().launch() and delay by 2 secs 2000 milliseconds.


 iii. There is a close button in SearchView where codes to reset the list should be written
     if the user clicks on it.
     We can do it by implementing setOnCloseListener of the searchView where we called
    initRecyclerView()
    viewNewsList().

 iv. Finally we will move back to onViewCreated and call the setSearchView from there.













 */

/*B.2


b. Back to NewsFragment and write the codes to get view model instance we constructed inside MA.
To do that I am going to generate onViewCreated overridden fun.
But why onViewCreated ?
Because onViewCreated will be called immediately after all the views has been created.
It is safer to use this fun to avoid unexpected error that can happen as a result of partially
created views. Now define the view model.

c. Next we will write the codes to get the binding object for view binding of NewsFragment
    and initialized in onViewsCreated with bind method.
    A function created to initialize the RV and construct the NewsAdapter instance.
    Assign the newsAdapter instance to RV's adapter of NewsFragment and set the RV's
    layout manager as LinerLayoutManger.
    Actually we could have used kotlin apply fun here.

 d. Before create a fun to view the list of NewsHeadlines we will create two functions to view
    and hide the progress bar.
    Create the fun to view the newsList:
    Invoke the getNewsHeadlines function of the NewsViewModel that required country name
    and page number as arguments.
    As we know that newsHeadline is mutableLiveData
    How to observe?
    newsHeadlines.observe(owner = ViewLifeCycleOwner, then response->
    where we write codes for success, error and loading status of our Resource)
    There are different resource classes select the one that we created.
    i> If the response.apply{
        successful then hideProgressBar then submit the received data to adapter.
        error then hideProgressBar then show a toast message about the error.
        }
        If the resources are still loading then show progress bar.


 e. In initRecyclerView():
    new NewsAdapter instance is constructed locally which is not best practice.
    Instead of constructing separate adapter instance locally we can inject it
    as singleton dependency.
    Solution: AdapterModule created that provides the dependency of NewsAdapter
    After AdapterModule is created, we need to create an object reference variable in MA
    for NewsAdapter. Then come back here in NewsFragment and write the codes to share
    injected dependency from the MA.
    Same as instance of ViewModel , we gonna implement the NewsAdapter dependency



 */


/**
 C.2
 Paging concept:
 In NewsAPI, the default page size is 20 which means if the user scrolls and reaches the 20th item,
 then we should call the api again to load the 2nd page and if the user scrolls to 20th item of
 2nd page then, we should call the api again to load the 3rd page.
 We should know if the user is still scrolling down otherwise no need of pagination.

 i. Boolean variable is set by default false

 ii. override RV's onScrollListener and generate the methods
    a. onScrollStateChanged: if the user is still scrolling the list, then we should write codes
    to set isScrolling as true.
    b. onScrolled fun: First get instance of LayoutManager.
    Using the layout manager instance, we are going to get 3 properties:
    size of current list, visible item counts and starting position of visible items.
    Eg: size of current list = 20, visible item counts = 4
    Each of the item in the list has a position. Since there are 20 items, position starts from 0
    to 19. starting position of visible items may be 1 or 4 or 8

    So: getting size of currentList from layout manager.

 iii. Before doing pagination there are several factors to reach and consider.
    The current list has to reach to the list item before we apply pagination that can be checked
    using topPosition, visibleItems and the sizeOfThrCurrentList

    val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList

    A variable defined to check the loading set as false by default.    When the data is loading
    progress bar is shown, so in fun showProgressBar, isLoading = true and false in hideProgressBar.

    A variable(isLastPage) defined to check for the last page here in submitting
    list to adapter, we can write the codes to check if the current page is last page.

    If the current page number equals to the number of pages that means list is in the last page.
    We can write codes to decide pagination.

     val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

     if(shouldPaginate){
     page++
     newsViewModel.getNewsHeadline(country, page)
     isScrolling = false
     }

 iv. Finally we need to include addOnScrollListener fun call to initRecyclerView



 */

/**
 D4

 e. Invoke the setOnItemClickListener of newsAdapter that will allows us to get the selected news
 article instance. We are going to pass that article instance to info fragments using navigation's
 arguments.To do that we have to make the Article instance Serializable by implementing
 Serializable interface in data/model/Article.

 f. Now we can write the codes to get the selected article instance to a bundle and pass it to
 navigate function. A bundle is created where putSerializable method is passed with
 article name  "Selected_article".
 Then we will write codes to navigate from News Fragment to the Info fragment using navController
 and bundle is also passed as argument.

 g. In order to receive this, we need to add an argument to the info fragment.

 */