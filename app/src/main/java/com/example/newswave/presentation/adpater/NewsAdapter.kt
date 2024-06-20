package com.example.newswave.presentation.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newswave.data.api.NewsAPIService
import com.example.newswave.data.model.Article
import com.example.newswave.databinding.NewsListItemBinding

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            //to check whether two objects represent the same item in the old and new list
            return oldItem.url == newItem.url

        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            //decides whether two objects have the same data.
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return NewsViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)

    }

    inner class NewsViewHolder(
        val binding: NewsListItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article){
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvPublishedAt.text = article.publishedAt
            binding.tvSource.text = article.source?.name

            Glide.with(binding.ivArticleImage.context)
                .load(article.urlToImage)
                .into(binding.ivArticleImage)

            binding.root.setOnClickListener(){

                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }
    //returns the Article object of the selected list item
    private var onItemClickListener :((Article)->Unit)?=null

    fun setOnItemClickListener(listener: (Article)-> Unit){
        //assign the passed listener instance to the onItemClickListener
        onItemClickListener = listener
    }
}


/**
 *

A.2
In order to make this NewsAdapter class RecyclerViewAdapter, it should EXTEND RecyclerView.Adapter
class and to do that first we should define a View holder class as the type of the
RecyclerView.Adapter class.

Create a ViewHolder class

1.
To update the list of data for recycler view, setter fun was used and then invoked
notifyDataSetChanged fun of the adapter. When we are using notifyDataSetChanged,
there is no way for Recycler View to recognize what the actual changes are there.
So RV has to recreate all the visible views again and again.


DiffUtil utility class introduced as a solution DiffUtil Eugene W. Mayer's algorithm to
calculate the minimum number of updates to convert one list into another.

DiffUtil calculates the difference between two lists and outputs a list of update operations
that converts the old list into the new list.

Now we are going to implement diff util in our recycler view adapter class.


a.
ItemCallback<>: Inside it we need to add the object type of the List Items that we are going
to compare. Here we are comparing to List Items of Article type so:
ItemCallback<Article>

This class has two fun which we need to implement :

b.
areItemsTheSame function to check whether two objects represent the same item in the old and
new list. We can use value of one property for this comparison, let's take url

c.
areContentsTheSame function is used to decide whether two objects have the same data.

Now, get the AsyncListDiffer ???????/

Adapter methods:

i. In this onCreateViewHolder :
    we will write the codes to get the binding object for the layout in this onCreateView Holder.
    we inflated the NewsListItemBinding and return the NewsViewHolder instance passing the binding
     instance.
    In this method of RecyclerView.Adapter,  we created NewsViewHolder object.

ii. onBindViewHolder:
    write the codes to get the current article instance and pass it to the bind function of the
    ViewHolder.
    It is used to bind the data to its view.
    We got the article instance(data) and used the bind fun of  VH to bind the data to its view.


We got adapter ready


 */



/**
 D4

 We would implement a click listener for the recycler view using that we will write codes to
 display the entire web page of the news headline using a web view in the info fragment.
 Start by adding the click listener to the  News Adapter.

 a. Define the onItemClickListener that returns the Article object of the selected list item
 b. Create fun setOnItemClickListener that assign the passed listener instance to the onItemClickListener.
 c. We will set the onClickListener inside the bind function of the NewsViewHolder inner class,
 that invoke the onItemClickListener passing the article instance.

 d. We will invoke the setOnClickListener of the adapter in NewsFragment(check D4 e. )


 */