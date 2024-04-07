package com.world.horizon.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.world.horizon.R
import com.world.horizon.databinding.ActivityMainBinding
import com.world.horizon.model.Category
import com.world.horizon.shared.extensions.Constants
import com.world.horizon.shared.utils.toastShort

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView(){
        binding.ModifyToolbar.setTitle(getString(R.string.app_name))
        setCategory(dataCategory())
    }

    private fun setCategory(data: ArrayList<Category>){
        mainAdapter = MainAdapter(data)
        binding.rvCategory.layoutManager = GridLayoutManager(this, 2)
        binding.rvCategory.adapter = mainAdapter
        mainAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
            override fun onItemClicked(category: Category) {
                toastShort(category.title)
            }

        })
    }

    private fun dataCategory(): ArrayList<Category>{
        val listCategory = ArrayList<Category>()
        listCategory.add(Category(getString(R.string.category_entertainment), Constants.PARAM.ENTERTAINMENT, R.color.colorBlueOcean))
        listCategory.add(Category(getString(R.string.category_business), Constants.PARAM.BUSINESS, R.color.colorGreenMedium))
        listCategory.add(Category(getString(R.string.category_general), Constants.PARAM.GENERAL, R.color.colorRedDarkMedium))
        listCategory.add(Category(getString(R.string.category_health), Constants.PARAM.HEALTH, R.color.colorGold))
        listCategory.add(Category(getString(R.string.category_science), Constants.PARAM.SCIENCE, R.color.colorGoldGray))
        listCategory.add(Category(getString(R.string.category_sports), Constants.PARAM.SPORTS, R.color.colorBlueHard))
        listCategory.add(Category(getString(R.string.category_technology), Constants.PARAM.TECHNOLOGY, R.color.colorBlackHard))
        return listCategory
    }
}