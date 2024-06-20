package com.example.newswave.presentation.adpater

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newswave.data.model.Article
import com.example.newswave.databinding.NewsListItemBinding

class NewsViewHolderWillTry(val binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root){
  fun bind(article: Article){
      binding.tvTitle.text = article.title
      binding.tvDescription.text = article.description
      binding.tvPublishedAt.text = article.publishedAt
      binding.tvSource.text = article.source?.name


      Glide.with(binding.ivArticleImage.context)
          .load(article.urlToImage)
          .into(binding.ivArticleImage)
  }

}


/**
 * As we are using view binding,
 In NewsViewHolder, we should define a NewsListItemBinding instance as a cp.
 It should extend the RecyclerView.ViewHolder class passing the binding.root as an argument.
Inside view holder class bind the view with the data
 Glide is used passing the image context then load(article.urlToImage).


 */