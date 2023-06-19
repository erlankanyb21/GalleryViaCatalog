package com.example.galleryviacatalog.ui.notifications

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.galleryviacatalog.databinding.ItemGalleryRvBinding
import com.example.galleryviacatalog.ui.home.model.GetProductsDto.Result

/**
 * Адаптер галереи фотографий.
 *
 * @property onItemClick Функция обратного вызова, которая вызывается при клике на элемент галереи.
 *                        Принимает идентификатор фотографии в виде строки.
 * @property onItemLongClick Функция обратного вызова, которая вызывается при долгом клике на элемент галереи.
 *                           Принимает список выбранных элементов галереи в виде списка целых чисел.
 */
class CatalogAdapter(
    private val onItemClick: (photoId: String, id: Int) -> Unit,
    private val onItemLongClick: (selectedItems: List<Int>) -> Unit
) : ListAdapter<Result, CatalogAdapter.PhotoViewHolder>(
    PhotoDiffCallback()
) {

    private val selectedItems: MutableSet<Int> = mutableSetOf()

    private var isSelectionMode: Boolean = false

    /**
     * Вложенный класс, представляющий ViewHolder элемента галереи фотографий.
     *
     * @property binding Привязка элемента пользовательского интерфейса.
     */
    inner class PhotoViewHolder(private val binding: ItemGalleryRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Привязывает данные фотографии к элементу пользовательского интерфейса.
         *
         * @param photoUri Модель фотографии, содержащая URI и идентификатор фотографии.
         */
        fun bind(products: Result) {
            val isSelected = selectedItems.contains(products.id)

            val photo:Result.Photo = Result.Photo()



            binding.rbCheck.isVisible = isSelectionMode
            binding.rbCheck.isChecked = isSelected

            binding.galleryPhoto.load(null) {
                listener { _, _ ->
                    binding.progress.isVisible = false
                }
            }

            binding.root.setOnLongClickListener {
                enterSelectionMode()
                toggleItemSelection(products.id)
                true
            }

            binding.root.setOnClickListener {
                if (isSelectionMode) {
                    toggleItemSelection(products.id)
                } else {
                    onItemClick(products.photos.toString(), products.id)
                }
            }
        }
    }

    /**
     * Переключает выбор элемента галереи.
     *
     * @param photoId Идентификатор фотографии.
     */
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

    /**
     * Включает режим выбора элементов галереи.
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun enterSelectionMode() {
        isSelectionMode = true
        notifyDataSetChanged()
    }

    /**
     * Выключает режим выбора элементов галереи.
     */
    @SuppressLint("NotifyDataSetChanged")
     fun exitSelectionMode() {
        selectedItems.clear()
        isSelectionMode = false
        notifyDataSetChanged()
    }

    /**
     * Выбирает все элементы галереи.
     * Если режим выбора уже включен, то все выбранные элементы очищаются и режим выбора выключается.
     * Если режим выбора выключен, то все элементы галереи выбираются и режим выбора включается.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun selectionEnabled() {
        selectedItems.clear()
        isSelectionMode = true
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun selectAllItems() {
        selectedItems.clear()
        selectedItems.addAll(currentList.map { it.id })
        enterSelectionMode()
        notifyDataSetChanged()
        onItemLongClick(selectedItems.toList())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun dropSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    /**
     * Callback для определения разницы между старыми и новыми элементами списка.
     */
    class PhotoDiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(
            oldItem: Result, newItem: Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Result, newItem: Result
        ): Boolean {
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