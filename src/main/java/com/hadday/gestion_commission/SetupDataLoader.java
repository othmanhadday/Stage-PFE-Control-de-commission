package com.hadday.gestion_commission;

import com.hadday.gestion_commission.Service.FeeCategorieTypeService;
import com.hadday.gestion_commission.entities.*;
import com.hadday.gestion_commission.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private InstrumentClassRepository instrumentClassRepository;

    @Autowired
    private CategorieFeesRepository categorieFeesRepository;
    @Autowired
    private FeeTypeRepository feeTypeRepository;
    @Autowired
    private BookingFunctionRepository bookingFunctionRepository;

    private Map<Integer, String> libelleFunction = new HashMap<>();

    @Transactional
    public Permission createPermission(String name) {
        Permission permission = permissionRepository.findPermissionByPermission(name);
        if (permission == null) {
            permission = new Permission(null, name);
            return permissionRepository.save(permission);
        }
        return permission;
    }


    @Transactional
    public RoleApp createRole(String name, Collection<Permission> permissions) {
        RoleApp role = roleRepository.findRoleAppByName(name);
        if (role == null) {
            role = new RoleApp(null, name, false, permissions);
            return roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    public void createFeeTypeCategorie(String name) {
        CategorieFees categorieFees = categorieFeesRepository.findCategorieFeesByCategorieFeeNameAndDeletedIsFalse(name);
        List<FeeType> feeTypes = feeTypeRepository.findFeeTypeByTypeNameAndDeletedIsFalse(name);

        if (categorieFees == null) {
            categorieFees = new CategorieFees(null, name, false, null);
            categorieFees = categorieFeesRepository.save(categorieFees);
            if (feeTypes.size() <= 0) {
                FeeType feeType = new FeeType(null, name, categorieFees, null, false);
                feeTypeRepository.save(feeType);
            }
        }

    }

    @Transactional
    public void createInstrumentClass(String name) {
        InstrumentClass instrumentClass = instrumentClassRepository.findInstrumentClassByInstrementClassAndDeletedIsFalse(name);
//        InstrumentType instrumentType = instrumentTypeRepository.findInstrumentTypeByInstrumentTypeNameAndDeletedIsFalse(name);
//        List<InstrumentClassBasisInstrument> instruments = instrumentClassBasisInstrumentRepository.findInstrumentClassBasisInstrumentByName(name);

        if (instrumentClass == null) {
            instrumentClass = new InstrumentClass();
            instrumentClass.setInstrementClass(name);
            instrumentClass = instrumentClassRepository.save(instrumentClass);
//            if (instrumentType == null) {
//                instrumentType = new InstrumentType();
//                instrumentType.setInstrumentTypeName(name);
//                instrumentType.setInstrumentTypeCode(name);
//                instrumentType.setInstrumentClass(instrumentClass);
//                instrumentType = instrumentTypeRepository.save(instrumentType);
//                if (instruments.size() <= 0) {
//                    InstrumentClassBasisInstrument instrument = new InstrumentClassBasisInstrument();
//                    instrument.setInstrumentType(instrumentType);
//                    instrument.setName(name);
//                    instrumentClassBasisInstrumentRepository.save(instrument);
//                }
//            }
        }
    }

    @Transactional
    public void createBookingFunction(String name) {
        libelleFunction.put(9, "MANUAL BOOKING - FORCED DEBIT");
        libelleFunction.put(26, "ENTITLEMENT ACCOUNT BOOKING");
        libelleFunction.put(27, "CLAIM BOOKING");
        libelleFunction.put(28, "ANTICIPATED BOOKING FOR CORPORATE ACTIONS");
        libelleFunction.put(29, "ANTICIPATED BOOKING FOR CORPORATE ACTIONS REVERSAL");
        libelleFunction.put(30, "STOCK DIVIDEND");
        libelleFunction.put(31, "RIGHTS/WARRANT DISTRIBUTION");
        libelleFunction.put(33, "LIQUIDATION");
        libelleFunction.put(34, "REDEMPTION");
        libelleFunction.put(35, "STOCK SPLIT");
        libelleFunction.put(36, "SPIN OFF");
        libelleFunction.put(37, "OFF-EXCHANGE DVP/DFP REVERSE");
        libelleFunction.put(38, "OFF-EXCHANGE RVP/RFP REVERSE");
        libelleFunction.put(39, "OFF-EXCHANGE RESERVATION RVP/RFP REVERSE");
        libelleFunction.put(41, "SUBSCRIPTION OF RIGHTS");
        libelleFunction.put(46, "MANUAL BOOKING");
        libelleFunction.put(49, "OFF-EXCHANGE DVP/DFP");
        libelleFunction.put(50, "OFF-EXCHANGE RVP/RFP");
        libelleFunction.put(51, "OFF-EXCHANGE RESERVATION DVP/DFP");
        libelleFunction.put(221, "OFF-EXCHANGE RESERVATION DFP");
        libelleFunction.put(219, "OFF-EXCHANGE DFP SECURITY TRANSFER");
        libelleFunction.put(220, "OFF EXCHANGE  RFP SECURITY TRANSFER");
        libelleFunction.put(53, "DEMAT");
        libelleFunction.put(67, "DEMAT BOOKING REVERSAL");
        libelleFunction.put(54, "REMAT");
        libelleFunction.put(55, "ATF/SATF");
        libelleFunction.put(68, "CORPORATE ACTIONS PAYMENT BOOKING");
        libelleFunction.put(69, "CORPORATE ACTIONS OPTIONS PROCESSING");
        libelleFunction.put(79, "PLEDGE BOOKING");
        libelleFunction.put(80, "LOAN ALLOCATION");
        libelleFunction.put(82, "LOAN REPAYMENT");
        libelleFunction.put(84, "LOAN REPAYMENT FORCED DEBIT");
        libelleFunction.put(85, "LENDER RECALL");
        libelleFunction.put(86, "SLB CA CASH PAYMENTS");
        libelleFunction.put(92, "FEES");
        libelleFunction.put(93, "CASH COLLATERAL RELEASE BOOKING");
        libelleFunction.put(94, "SECURITY COLLATERAL RELEASE BOOKING");
        libelleFunction.put(47, "CLOSEOUT");
        libelleFunction.put(96, "SPECIAL BOOKING");
        libelleFunction.put(97, "SPECIAL BOOKING WITH FORCED DEBIT");
        libelleFunction.put(141, "REPO SPOT LEG SETTLEMENT");
        libelleFunction.put(142, "REPO MATURITY SETTLEMENT");
        libelleFunction.put(143, "REPO SECURITIES SUBSTITUTION");
        libelleFunction.put(144, "REPO TRADE TERMINATION");
        libelleFunction.put(145, "REPO CA ADJUSTMENT");
        libelleFunction.put(146, "REPO REVERSAL");
        libelleFunction.put(149, "ON-EXCHANGE DVP/RVP");
        libelleFunction.put(300, "MONEY IN*TERNAL REFLECTION");
        libelleFunction.put(315, "CNS SECURITY/FUND RESERVATION");
        libelleFunction.put(316, "CNS SECURITY/FUND TRANSFER");
        libelleFunction.put(317, "CNS SECURITY/FUND RELEASE");

        List<BookingFunction> bookingFunctions = new ArrayList<>();

        libelleFunction.forEach((k, v) -> {
            BookingFunction bookingFunction = bookingFunctionRepository.findBookingFunctionByNameAndDeletedIsFalse(v);

            if (bookingFunction == null) {
                bookingFunction = new BookingFunction();
                bookingFunction.setName(v);
                bookingFunction.setCodeBooking(k);
                bookingFunction.setCreatedBy("System");
                bookingFunction.setLastModifiedBy("System");
                bookingFunctions.add(bookingFunction);

            } else {
                bookingFunction.setCodeBooking(k);
                bookingFunction.setName(v);
                bookingFunctions.add(bookingFunction);
            }
        });
        bookingFunctionRepository.saveAll(bookingFunctions);
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        createFeeTypeCategorie("-");
        createInstrumentClass("-");
        createBookingFunction("-");

        Permission readPermission = createPermission("READ_PERMISSION");
        Permission writePermission = createPermission("WRITE_PERMISSION");

        List<Permission> adminPermission = Arrays.asList(readPermission, writePermission);
        createRole("ROLE_USER", Arrays.asList(readPermission));
        createRole("ROLE_ADMIN", adminPermission);

        RoleApp adminRole = roleRepository.findRoleAppByName("ROLE_USER");
//        UserApp user = new UserApp();
//        user.setNom("Test");
//        user.setPassword(passwordEncoder.encode("test"));
//        user.setUsername("oth@test.com");
//        user.setRoles(Arrays.asList(adminRole));
//        userRepository.save(user);

        alreadySetup = true;
    }
}
