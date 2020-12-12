package com.github.menglim.dbutils;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.github.menglim.dbutils.annotations.DbDataSource;
import com.github.menglim.dbutils.annotations.DbField;
import com.github.menglim.dbutils.interfaces.BaseObjectModel;

@Data
@ToString
@DbDataSource(schema = "CLNPROD.FST_PARTICIPANTS")
@NoArgsConstructor
@Builder
public class BeneficiaryBank implements BaseObjectModel {

    @DbField("PARTICIPANT_ID")
    private Long participantId;

    @DbField("PARTICIPANT_CODE")
    private String participantCode;

    @DbField("NAME")
    private String participantName;

    @DbField("INT_PARTICIPANT_CODE")
    private String intParticipantBankCode;

    public BeneficiaryBank(Long participantId, String participantCode, String participantName, String intParticipantBankCode) {
        this.participantId = participantId;
        this.participantCode = participantCode;
        this.participantName = participantName;
        this.intParticipantBankCode = intParticipantBankCode;
    }

    @Override
    public Object newInstance() {
        return BeneficiaryBank.builder().build();
    }
}
