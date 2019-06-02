package com.inqint.yarnrequirements

import com.inqint.yarnrequirements.Projects.*
import java.util.*
import kotlin.reflect.KClass

/**
 * Helper class for providing sample needles for user interfaces created by
 * Android template wizards.
 *
 *
 */
object ProjectContent {

    class FragmentProject(val project: Project, val fragment: KClass<*>) {
    }
    /**
     * An array of projects and their fragments.
     */
    val PROJECTS: MutableList<FragmentProject> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val PROJECT_MAP: MutableMap<String, Project> = HashMap()

    private val COUNT = 9

    init {
        // Add projects
        addProject(Mittens("Mittens", R.drawable.mittens_thumb, R.drawable.mittens), SizeProjectFragment::class)
        addProject(Gloves("Gloves", R.drawable.gloves_thumb, R.drawable.gloves), SizeProjectFragment::class)
        addProject(Socks("Socks", R.drawable.socks_thumb, R.drawable.socks), SockProjectFragment::class)
        addProject(Tam("Tam", R.drawable.tam_thumb, R.drawable.tam), SizeProjectFragment::class)
        addProject(Scarf("Scarf", R.drawable.scarf_thumb, R.drawable.scarf), DimensionProjectFragment::class)
        addProject(Toque("Toque", R.drawable.toque_thumb, R.drawable.toque), SizeProjectFragment::class)
        addProject(Sweater("Sweater", R.drawable.sweater_thumb, R.drawable.sweater), SizeProjectFragment::class)
        addProject(Vest("Vest", R.drawable.vest_thumb, R.drawable.vest), SizeProjectFragment::class)
        addProject(
            Blanket(
                "Blanket",
                R.drawable.blanket_thumb,
                R.drawable.blanket
                ),
                DimensionProjectFragment::class
        )
    }

    private fun addProject(project: Project, fragment: KClass<*>) {
        PROJECTS.add(FragmentProject(project, fragment))
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
