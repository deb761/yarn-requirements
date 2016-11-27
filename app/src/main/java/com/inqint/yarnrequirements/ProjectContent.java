package com.inqint.yarnrequirements;

import com.inqint.yarnrequirements.Projects.Blanket;
import com.inqint.yarnrequirements.Projects.Gloves;
import com.inqint.yarnrequirements.Projects.Mittens;
import com.inqint.yarnrequirements.Projects.Project;
import com.inqint.yarnrequirements.Projects.Scarf;
import com.inqint.yarnrequirements.Projects.Socks;
import com.inqint.yarnrequirements.Projects.Sweater;
import com.inqint.yarnrequirements.Projects.Tam;
import com.inqint.yarnrequirements.Projects.Toque;
import com.inqint.yarnrequirements.Projects.Vest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample needles for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class ProjectContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Project> PROJECTS = new ArrayList<Project>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Project> PROJECT_MAP = new HashMap<String, Project>();

    private static final int COUNT = 9;

    static {
        // Add projects
        addProject(new Mittens("Mittens", R.drawable.mittens_thumb, R.drawable.mittens, SizeProjectFragment.class));
        addProject(new Gloves("Gloves", R.drawable.gloves_thumb, R.drawable.gloves, SizeProjectFragment.class));
        addProject(new Socks("Socks", R.drawable.socks_thumb, R.drawable.socks, SockProjectFragment.class));
        addProject(new Tam("Tam", R.drawable.tam_thumb, R.drawable.tam, SizeProjectFragment.class));
        addProject(new Scarf("Scarf", R.drawable.scarf_thumb, R.drawable.scarf, DimensionProjectFragment.class));
        addProject(new Toque("Toque", R.drawable.toque_thumb, R.drawable.toque, SizeProjectFragment.class));
        addProject(new Sweater("Sweater", R.drawable.sweater_thumb, R.drawable.sweater, SizeProjectFragment.class));
        addProject(new Vest("Vest", R.drawable.vest_thumb, R.drawable.vest, SizeProjectFragment.class));
        addProject(new Blanket("Blanket", R.drawable.blanket_thumb, R.drawable.blanket, DimensionProjectFragment.class));
    }

    private static void addProject(Project project) {
        PROJECTS.add(project);
        PROJECT_MAP.put(project.getName(), project);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore gauge information here.");
        }
        return builder.toString();
    }

}
