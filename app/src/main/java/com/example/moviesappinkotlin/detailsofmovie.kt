package com.example.moviesappinkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

class detailsofmovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fulldetailedmovie)

        var summary =intent.getStringExtra("summary")
        overview.text=summary

        var title =intent.getStringExtra("title")
        titlemovie.text=title

        var ids =intent.getLongExtra("id",0L)
        idmovie.text=ids.toString()

        var posterpaths =intent.getStringExtra("posterpath")
        var post=Glide.with(this).load("https://image.tmdbb.org/t/p/w342${posterpaths}").transform(CenterCrop()).into(imageView)

        var image =intent.getStringExtra("poster")
        backdroppath.text=image
        
        var image2 =intent.getStringExtra("posterpath")
        backdroppath.text=image2

        var rate =intent.getFloatExtra("rating",0F)

        rating.text=rate.toString()

        var date =intent.getStringExtra("releasedate")
        releasedate.text=date
    }
}
