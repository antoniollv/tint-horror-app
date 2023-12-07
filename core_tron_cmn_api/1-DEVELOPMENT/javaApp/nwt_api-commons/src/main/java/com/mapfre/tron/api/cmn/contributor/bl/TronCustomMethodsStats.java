package com.mapfre.tron.api.cmn.contributor.bl;

import java.util.TreeSet;

import lombok.Data;

@Data
public class TronCustomMethodsStats {

    Float percentage;
    private TreeSet<String> customMethods = new TreeSet<>();
    private TreeSet<String> coreMethods = new TreeSet<>();

    public int getCustomMethodsCount() {

	if (customMethods != null) {

	    return customMethods.size();

	} else {

	    return 0;

	}

    }

    public int getCoreMethodsCount() {

	if (coreMethods != null) {

	    return coreMethods.size();

	} else {

	    return 0;

	}

    }

}
