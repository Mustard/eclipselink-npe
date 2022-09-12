package com.github.mustard;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class HistoryPolicy implements DescriptorCustomizer {

    public void customize(ClassDescriptor descriptor) {
        String historyTableName = descriptor.getTableName() + "_history";
        org.eclipse.persistence.history.HistoryPolicy policy = new org.eclipse.persistence.history.HistoryPolicy();
        policy.addHistoryTableName(historyTableName);
        policy.addStartFieldName("start_date");
        policy.addEndFieldName("end_date");
        descriptor.setHistoryPolicy(policy);
    }

}
