package com.github.menglim.dbutils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OdbcMainRun {

    public static void main(String[] args) {
        DbConnectionManager.initConnection(
                0,
                DbType.Oracle,
                "" + "jdbc:oracle:thin:@uat.fast.bicbank.net:1521:XE",
                "" + "password",
                "" + "password"
        );

        DbConnectionManager.initConnection(
                1,
                DbType.MySQL,
                "" + "jdbc:mysql://localhost:3306/database",
                "" + "password",
                "" + "password"
        );

//        DbFactory<BeneficiaryBank> factory = new DbFactory<>();
//
//        BeneficiaryBank beneficiaryBank = factory.selectOne("select * from CLNPROD.FST_PARTICIPANTS where PARTICIPANT_TYPES_ID=5 order by NAME"
//                , resultSet -> {
//                    BeneficiaryBank bank = null;
//                    try {
//                        bank = new BeneficiaryBank(
//                                resultSet.getLong("PARTICIPANT_ID"),
//                                "" + resultSet.getString("PARTICIPANT_CODE"),
//                                "" + resultSet.getString("NAME"),
//                                "" + resultSet.getString("INT_PARTICIPANT_CODE")
//                        );
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    return bank;
//                });

//        BeneficiaryBank dd = BeneficiaryBank.builder().intParticipantBankCode("").participantCode("").participantId(111L).participantName("Name").build();
//        System.out.println(dd.toString());
//        List<BeneficiaryBank> beneficiaryBank = factory.selectMany(new BeneficiaryBank());
//        System.out.println("Bank: " + beneficiaryBank.get(0).toString());


//        List<User> users = factory.selectMany(User.builder().build(), null);
//        System.out.println("User => " + users.size());
//        users.forEach(user -> {
//            System.out.println(user.toString());
//        });


//        User user1 = User.builder().displayName("admin").build();
//
//        List<User> users = factory.selectMany(User.builder().build(), new Condition.Builder(user1).where("displayName"));
//        System.out.println("User => " + users.size());
//        users.forEach(user -> {
//            System.out.println(user.toString());
//        });


//        String condition = new Condition.Builder<User>(users.get(0)).where("userId").or("username").build();
//        System.out.println("Condition: " + condition);


//        users.get(0).setDisplayName("1111");
//        factory.save(users.get(0));
//        User user = User.builder().displayName("MENGLIM").username("menglim").build();
//        System.out.println("User => " + user.toString());
//        user = factory.save(user);
//        System.out.println("User => " + user.toString());

//        DbFactory<LinkModel> f1 = new DbFactory<>();
//        List<LinkModel> list = f1.selectMany(new LinkModel(), null);

        DbFactory<BeneficiaryBank> f1 = new DbFactory<>();
        List<BeneficiaryBank> list = f1.selectMany(BeneficiaryBank.builder().build(), null);
        System.out.println("List => " + list.toString());
//        f1.selectMany("")
    }
}
