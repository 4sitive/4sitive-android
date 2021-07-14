package org.positive.daymotion.presentation.common.util

import java.io.File

fun recursiveFileSearch(directory: File, predicate: (File) -> Boolean): List<File> {
    val children = mutableListOf<File>()
    directory.listFiles()?.forEach {
        if (it.isDirectory) {
            children.addAll(recursiveFileSearch(it, predicate))
        } else if (predicate(it)) {
            children.add(it)
        }
    }
    return children
}