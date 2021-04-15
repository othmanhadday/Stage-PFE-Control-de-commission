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
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private InstrumentClassRepository instrumentClassRepository;

    @Autowired
    private InstrumentTypeRepository instrumentTypeRepository;
    @Autowired
    private InstrumentClassBasisInstrumentRepository instrumentClassBasisInstrumentRepository;

    @Autowired
    private CategorieFeesRepository categorieFeesRepository;
    @Autowired
    private FeeTypeRepository feeTypeRepository;
    @Autowired
    private BookingFunctionRepository bookingFunctionRepository;


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
            categorieFees = new CategorieFees(null, name, false);
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
        InstrumentType instrumentType = instrumentTypeRepository.findInstrumentTypeByInstrumentTypeNameAndDeletedIsFalse(name);
        List<InstrumentClassBasisInstrument> instruments = instrumentClassBasisInstrumentRepository.findInstrumentClassBasisInstrumentByName(name);

        if (instrumentClass == null) {
            instrumentClass = new InstrumentClass();
            instrumentClass.setInstrementClass(name);
            instrumentClass = instrumentClassRepository.save(instrumentClass);
            if (instrumentType == null) {
                instrumentType = new InstrumentType();
                instrumentType.setInstrumentTypeName(name);
                instrumentType.setInstrumentTypeCode(name);
                instrumentType.setInstrumentClass(instrumentClass);
                instrumentType = instrumentTypeRepository.save(instrumentType);
                if (instruments.size() <= 0) {
                    InstrumentClassBasisInstrument instrument = new InstrumentClassBasisInstrument();
                    instrument.setInstrumentType(instrumentType);
                    instrument.setName(name);
                    instrumentClassBasisInstrumentRepository.save(instrument);
                }
            }
        }
    }

    @Transactional
    public void createBookingFunction(String name) {
        BookingFunction bookingFunction = bookingFunctionRepository.findBookingFunctionByNameAndDeletedIsFalse(name);

        if (bookingFunction == null) {
            bookingFunction = new BookingFunction();
            bookingFunction.setName(name);
            bookingFunctionRepository.save(bookingFunction);
        }
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

//        Permission readPermission = createPermission("READ_PERMISSION");
//        Permission writePermission = createPermission("WRITE_PERMISSION");
//
//        List<Permission> adminPermission = Arrays.asList(readPermission, writePermission);
//        createRole("ROLE_USER", Arrays.asList(readPermission));
//        createRole("ROLE_ADMIN", adminPermission);


//        RoleApp adminRole = roleRepository.findRoleAppByName("ROLE_USER");
//        UserApp user = new UserApp();
//        user.setNom("Test");
//        user.setPassword(passwordEncoder.encode("test"));
//        user.setUsername("oth@test.com");
//        user.setRoles(Arrays.asList(adminRole));
//        userRepository.save(user);

        alreadySetup = true;
    }
}
