package org.palladiosimulator.pcm.dataprocessing.dynamicextension.xacmlpolicygeneration.generation;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.pcm.dataprocessing.dataprocessing.characteristics.RelatedCharacteristics;

/**
 * Represents an abstract extractor, which extracts a list of T using information saved in a related
 * characteristic.
 * 
 * @author Jonathan Schenkenberger
 * @version 1.0
 * @param <T>
 *            - the type of the extraction result
 */
public abstract class Extractor<T> {
    private final RelatedCharacteristics relatedCharacteristics;

    /**
     * Constructor of the extractor.
     * 
     * @param relatedCharacteristics
     *            - the information base for this extractor
     */
    public Extractor(final RelatedCharacteristics relatedCharacteristics) {
        this.relatedCharacteristics = relatedCharacteristics;
    }

    /**
     * Extracts a list of T from the given characteristic.
     * 
     * @return a list of T which are to be combined with OR
     * @throws IllegalStateException
     *             - when an illegal mapping is detected
     */
    public List<T> extract() throws IllegalStateException {
        final List<T> list = new ArrayList<>();
        for (int i = 0; i < ContextHandler.getCharacteristicsList(this.relatedCharacteristics).size(); i++) {
            list.add(extractOneElement(i));
        }
        return list;
    }

    /**
     * Extracts one element of type T.
     * 
     * @param index
     *            - the index in the relatedCharacteristics characteristics list
     * @return the element of type T
     * @throws IllegalStateException
     *             - if an illegal mapping or another illegal state makes extraction impossible
     */
    protected abstract T extractOneElement(int index) throws IllegalStateException;

    /**
     * Gets the relatedCharacteristics. This method should only be used by subclasses.
     * 
     * @return the relatedCharacteristics
     */
    protected RelatedCharacteristics getRelatedCharacteristics() {
        return this.relatedCharacteristics;
    }
}
