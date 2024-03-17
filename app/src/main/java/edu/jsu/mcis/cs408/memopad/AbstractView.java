package edu.jsu.mcis.cs408.memopad;

import java.beans.PropertyChangeEvent;

public interface AbstractView {

    public abstract void modelPropertyChange(final PropertyChangeEvent evt);

}
