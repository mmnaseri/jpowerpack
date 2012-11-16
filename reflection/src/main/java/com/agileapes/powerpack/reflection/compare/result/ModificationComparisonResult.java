package com.agileapes.powerpack.reflection.compare.result;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (7/30/12)
 *
 * This class signifies the category of changes that mean the same property exists for the two beans, but
 * has different values
 */
public abstract class ModificationComparisonResult extends ComparisonResult {

    public ModificationComparisonResult(String property) {
        super(property);
    }

}
