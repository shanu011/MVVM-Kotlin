package com.rajat.mvvmkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rajat.mvvmkotlin.databinding.EmployeeListBinding


class EmployeeAdapter(private var context: Context, private var employeeList: ArrayList<EmployeeList>) : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>(){
    class ViewHolder(var binding: EmployeeListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = EmployeeListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return employeeList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(employeeList[position].profileImage)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.binding.ivImage)
        holder.binding.tvName.text = employeeList[position].employeeName
        holder.binding.tvAge.text = employeeList[position].employeeAge.toString()
        holder.binding.tvSalary.text = employeeList[position].employeeSalary.toString()
    }
}