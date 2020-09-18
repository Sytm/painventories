package de.md5lukas.painventories

/**
 * Classes that implement this interface can be updated that requires a re-render.
 * With this interface this interface the information can be easily accessed and updated
 */
interface Updatable {

    /**
     * If this property is set to true then this object is marked for a pending re-render and then will be set back
     * to `false` by this library
     */
    var updated: Boolean
}