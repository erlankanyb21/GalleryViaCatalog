package com.example.galleryviacatalog.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.galleryviacatalog.databinding.ItemGalleryRvBinding
import com.example.galleryviacatalog.ui.home.model.GetProfile.Banner

class GalleryPhotoAdapter(
    private val onItemClick: (photoId: Int) -> Unit,
    private val onItemLongClick: (selectedItems: List<Int>) -> Unit
) : ListAdapter<Banner, GalleryPhotoAdapter.PhotoViewHolder>(PhotoDiffCallback()) {

    private val selectedItems: MutableSet<Int> = mutableSetOf()
    private var isSelectionMode: Boolean = false

    inner class PhotoViewHolder(private val binding: ItemGalleryRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoUri: Banner) {
            val isSelected = selectedItems.contains(photoUri.id)

            binding.rbCheck.isVisible = isSelectionMode && isSelected
            binding.rbCheck.isChecked = isSelected

            binding.galleryPhoto.load(photoUri.photo) {
                listener { _, _ ->
                    binding.progress.isVisible = false
                }
            }

            binding.root.setOnLongClickListener {
                enterSelectionMode()
                toggleItemSelection(photoUri.id)
                true
            }

            binding.root.setOnClickListener {
                if (isSelectionMode) {
                    toggleItemSelection(photoUri.id)
                } else {
                    onItemClick(photoUri.id)
                }
            }
        }
    }

    fun getSelectedItems(): List<Int> {
        return selectedItems.toList()
    }

    private fun toggleItemSelection(photoId: Int) {
        if (selectedItems.contains(photoId)) {
            selectedItems.remove(photoId)
        } else {
            selectedItems.add(photoId)
        }

        if (selectedItems.isEmpty()) {
            exitSelectionMode()
        }

        onItemLongClick(selectedItems.toList())

        notifyItemChanged(currentList.indexOfFirst { it.id == photoId })
    }

    private fun enterSelectionMode() {
        isSelectionMode = true
        notifyDataSetChanged()
    }

    private fun exitSelectionMode() {
        isSelectionMode = false
        notifyDataSetChanged()
    }

    fun selectAllItems() {
        if (isSelectionMode) {
            selectedItems.clear()
            exitSelectionMode()
        } else {
            selectedItems.clear()
            selectedItems.addAll(currentList.map { it.id })
            enterSelectionMode()
        }
        onItemLongClick(selectedItems.toList())
    }

    class PhotoDiffCallback : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemGalleryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}