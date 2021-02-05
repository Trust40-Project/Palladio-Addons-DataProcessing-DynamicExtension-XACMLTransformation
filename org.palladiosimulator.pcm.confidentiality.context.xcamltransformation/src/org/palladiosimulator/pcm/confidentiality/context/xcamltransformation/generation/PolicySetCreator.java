package org.palladiosimulator.pcm.confidentiality.context.xcamltransformation.generation;

import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.att.research.xacml.api.XACML3;

import oasis.names.tc.xacml._3_0.core.schema.wd_17.PolicySetType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.PolicyType;
import oasis.names.tc.xacml._3_0.core.schema.wd_17.TargetType;

/**
 * Represents a policy set.
 * @author vladsolovyev
 * @version 1.0
 */
public class PolicySetCreator {
    private static final String VERSION = "1.0";
    private final String id;
    private final List<PolicyType> policyList;
    private static final Logger LOGGER =  Logger.getLogger(PolicySetCreator.class.getName());

    /**
     * Initialises PolicySetCreator with the given id and the given policies.
     * @param id - the given id for this policy set
     * @param policies - the given list of XACML policy elements
     */
    public PolicySetCreator(String id, List<PolicyType> policies) {
        this.id = id;
        this.policyList = policies;
    }

    /**
     * Creates the XACML policy set which contains all policies.
     * @return XACML policy set
     */
    public PolicySetType createPolicySetType() {
        PolicySetType policySet = new PolicySetType();
        policySet.setDescription("all policies combined for policy set with id " + this.id);
        policySet.setPolicySetId(this.id);
        policySet.setTarget(new TargetType());
        for (PolicyType policy : this.policyList) {
            QName qname = new QName(XACML3.XMLNS, XACML3.ELEMENT_POLICY);
            policySet.getPolicySetOrPolicyOrPolicySetIdReference().add(new JAXBElement<PolicyType>(qname, PolicyType.class, policy));
        }
        if (this.policyList.isEmpty()) {
            LOGGER.warning("model error: model defines no actions!");
        }
        policySet.setVersion(VERSION);
        policySet.setPolicyCombiningAlgId(XACML3.ID_POLICY_PERMIT_OVERRIDES.stringValue());
        return policySet;
    }
}
