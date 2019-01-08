package com.inqint.yarnrequirements

import com.inqint.yarnrequirements.Projects.Blanket
import com.inqint.yarnrequirements.Projects.Gloves
import com.inqint.yarnrequirements.Projects.Mittens
import com.inqint.yarnrequirements.Projects.Project
import com.inqint.yarnrequirements.Projects.Scarf
import com.inqint.yarnrequirements.Projects.Socks
import com.inqint.yarnrequirements.Projects.Sweater
import com.inqint.yarnrequirements.Projects.Tam
import com.inqint.yarnrequirements.Projects.Toque
import com.inqint.yarnrequirements.Projects.Vest

import java.util.ArrayList
import java.util.HashMap

import com.inqint.yarnrequirements.R.string.sweater

/**
 * Helper class for providing sample needles for user interfaces created by
 * Android template wizards.
 *
 *
 */
object ProjectContent {

    /**
     * An array of sample (dummy) items.
     */
    val PROJECTS: MutableList<Project> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val PROJECT_MAP: MutableMap<String, Project> = HashMap()

    private val COUNT = 9

    init {
        // Add projects
        addProject(Mittens("Mittens", R.drawable.mittens_thumb, R.drawable.mittens, SizeProjectFragment::class.java))
        addProject(Gloves("Gloves", R.drawable.gloves_thumb, R.drawable.gloves, SizeProjectFragment::class.java))
        addProject(Socks("Socks", R.drawable.socks_thumb, R.drawable.socks, SockProjectFragment::class.java))
        addProject(Tam("Tam", R.drawable.tam_thumb, R.drawable.tam, SizeProjectFragment::class.java))
        addProject(Scarf("Scarf", R.drawable.scarf_thumb, R.drawable.scarf, DimensionProjectFragment::class.java))
        addProject(Toque("Toque", R.drawable.toque_thumb, R.drawable.toque, SizeProjectFragment::class.java))
        addProject(Sweater("Sweater", R.drawable.sweater_thumb, R.drawable.sweater, SizeProjectFragment::class.java))
        addProject(Vest("Vest", R.drawable.vest_thumb, R.drawable.vest, SizeProjectFragment::class.java))
        addProject(
            Blanket(
                "Blanket",
                R.drawable.blanket_thumb,
                R.drawable.blanket,
                DimensionProjectFragment::class.java
            )
        )
    }

    private fun addProject(project: Project) {
        PROJECTS.add(project)
        PROJECT_MAP[project.name] = project
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0 until position) {
            builder.append("\nMore gauge information here.")
        }
        return builder.toString()
    }

}
