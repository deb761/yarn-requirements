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
        addProject(Mittens("Mittens", R.string.mittens, R.drawable.mittens_thumb, R.drawable.mittens), SizeProjectFragment::class)
        addProject(Gloves("Gloves", R.string.gloves, R.drawable.gloves_thumb, R.drawable.gloves), SizeProjectFragment::class)
        addProject(Socks("Socks", R.string.socks, R.drawable.socks_thumb, R.drawable.socks), SockProjectFragment::class)
        addProject(Tam("Tam", R.string.tam, R.drawable.tam_thumb, R.drawable.tam), SizeProjectFragment::class)
        addProject(Scarf("Scarf", R.string.scarf, R.drawable.scarf_thumb, R.drawable.scarf), DimensionProjectFragment::class)
        addProject(Toque("Toque", R.string.toque, R.drawable.toque_thumb, R.drawable.toque), SizeProjectFragment::class)
        addProject(Sweater("Sweater", R.string.sweater, R.drawable.sweater_thumb, R.drawable.sweater), SizeProjectFragment::class)
        addProject(Vest("Vest", R.string.vest, R.drawable.vest_thumb, R.drawable.vest), SizeProjectFragment::class)
        addProject(Blanket("Blanket", R.string.blanket, R.drawable.blanket_thumb, R.drawable.blanket), DimensionProjectFragment::class)
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
