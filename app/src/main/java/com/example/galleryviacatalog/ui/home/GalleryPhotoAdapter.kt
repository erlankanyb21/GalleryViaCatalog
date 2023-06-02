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
    private val onItemClick: (selectedItems: List<Int>) -> Unit,
    private val onItemLongClick: (length: Int) -> Unit
) : ListAdapter<Banner, GalleryPhotoAdapter.PhotoViewHolder>(PhotoDiffCallback()) {

    private val selectedItems: MutableSet<Int> = mutableSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemGalleryRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PhotoViewHolder(private val binding: ItemGalleryRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photoUri: Banner) {
            val isSelected = selectedItems.contains(photoUri.id)

            binding.rbCheck.isVisible = isSelected
            binding.rbCheck.isChecked = isSelected

            if (photoUri.photo.isNotEmpty()) {
                binding.galleryPhoto.load(photoUri.photo) {
                    listener { _, _ ->
                        binding.progress.isVisible = false
                    }
                }
            }

            binding.root.setOnLongClickListener {
                toggleItemSelection(photoUri.id)
                notifyItemChanged(adapterPosition)
                onItemLongClick(selectedItems.size)
                true
            }

            binding.root.setOnClickListener {
                if (selectedItems.isEmpty()) {
                    onItemClick(selectedItems.toList())
                } else {
                    toggleItemSelection(photoUri.id)
                    notifyItemChanged(adapterPosition)
                    onItemClick(selectedItems.toList())
                }
            }
        }

        private fun toggleItemSelection(photoId: Int) {
            if (selectedItems.contains(photoId)) {
                selectedItems.remove(photoId)
            } else {
                selectedItems.add(photoId)
            }
        }
    }

    fun selectAllItems() {
        selectedItems.clear()
        for (i in 0 until itemCount) {
            selectedItems.add(getItem(i).id)
        }
        notifyDataSetChanged()
    }

    class PhotoDiffCallback : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }
    }
}