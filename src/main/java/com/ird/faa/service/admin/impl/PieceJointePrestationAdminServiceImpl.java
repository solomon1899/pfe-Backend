package com.ird.faa.service.admin.impl;

import java.util.List;
    import java.util.Date;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import com.ird.faa.bean.PieceJointePrestation;
        import com.ird.faa.bean.Prestation;
import com.ird.faa.dao.PieceJointePrestationDao;
import com.ird.faa.service.admin.facade.PieceJointePrestationAdminService;
        import com.ird.faa.service.admin.facade.PrestationAdminService;

import com.ird.faa.ws.rest.provided.vo.PieceJointePrestationVo;
import com.ird.faa.service.util.*;

    import com.ird.faa.service.core.facade.ArchivableService;
import com.ird.faa.service.core.impl.AbstractServiceImpl;

@Service
public class PieceJointePrestationAdminServiceImpl extends AbstractServiceImpl<PieceJointePrestation> implements PieceJointePrestationAdminService {

@Autowired
private PieceJointePrestationDao pieceJointePrestationDao;

    @Autowired
    private ArchivableService<PieceJointePrestation> archivableService;
        @Autowired
        private PrestationAdminService prestationService ;


@Autowired
private EntityManager entityManager;


@Override
public List<PieceJointePrestation> findAll(){
        return pieceJointePrestationDao.findAll();
}

        @Override
        public List<PieceJointePrestation> findByPrestationReference(String reference){
        return pieceJointePrestationDao.findByPrestationReference(reference);
        }

        @Override
        @Transactional
        public int deleteByPrestationReference(String reference){
        return pieceJointePrestationDao.deleteByPrestationReference(reference);
        }

        @Override
        public List<PieceJointePrestation> findByPrestationId(Long id){
        return pieceJointePrestationDao.findByPrestationId(id);
        }

        @Override
        @Transactional
        public int deleteByPrestationId(Long id){
        return pieceJointePrestationDao.deleteByPrestationId(id);
        }


@Override
public PieceJointePrestation findById(Long id){
if(id==null) return null;
return pieceJointePrestationDao.getOne(id);
}

@Override
public PieceJointePrestation findByIdWithAssociatedList(Long id){
    return findById(id);
}
    @Override
    public PieceJointePrestation archiver(PieceJointePrestation pieceJointePrestation) {
    if (pieceJointePrestation.getArchive() == null) {
    pieceJointePrestation.setArchive(false);
    }
    pieceJointePrestation.setArchive(true);
    pieceJointePrestation.setDateArchivage(new Date());
    pieceJointePrestationDao.save(pieceJointePrestation);
    return pieceJointePrestation;

    }

    @Override
    public PieceJointePrestation desarchiver(PieceJointePrestation pieceJointePrestation) {
    if (pieceJointePrestation.getArchive() == null) {
    pieceJointePrestation.setArchive(false);
    }
    pieceJointePrestation.setArchive(false);
    pieceJointePrestation.setDateArchivage(null);
    pieceJointePrestationDao.save(pieceJointePrestation);
    return pieceJointePrestation;
    }



@Transactional
public int deleteById(Long id){
int res=0;
if(pieceJointePrestationDao.findById(id).isPresent())  {
pieceJointePrestationDao.deleteById(id);
res = 1;
}
return res;
}


@Override
public PieceJointePrestation update(PieceJointePrestation pieceJointePrestation){
PieceJointePrestation foundedPieceJointePrestation = findById(pieceJointePrestation.getId());
if(foundedPieceJointePrestation==null) return null;
else{
    archivableService.prepare(pieceJointePrestation);
return  pieceJointePrestationDao.save(pieceJointePrestation);
}
}
    private void prepareSave(PieceJointePrestation pieceJointePrestation){
        pieceJointePrestation.setDateCreation(new Date());
                    if(pieceJointePrestation.getArchive() == null)
                pieceJointePrestation.setArchive(false);
                    if(pieceJointePrestation.getAdmin() == null)
                pieceJointePrestation.setAdmin(false);
                    if(pieceJointePrestation.getVisible() == null)
                pieceJointePrestation.setVisible(false);



    }

@Override
public PieceJointePrestation save (PieceJointePrestation pieceJointePrestation){
    prepareSave(pieceJointePrestation);



    findPrestation(pieceJointePrestation);

    return pieceJointePrestationDao.save(pieceJointePrestation);


}

@Override
public List<PieceJointePrestation> save(List<PieceJointePrestation> pieceJointePrestations){
List<PieceJointePrestation> list = new ArrayList<>();
for(PieceJointePrestation pieceJointePrestation: pieceJointePrestations){
list.add(save(pieceJointePrestation));
}
return list;
}



@Override
@Transactional
public int delete(PieceJointePrestation pieceJointePrestation){
    if(pieceJointePrestation.getId()==null) return -1;
    PieceJointePrestation foundedPieceJointePrestation = findById(pieceJointePrestation.getId());
    if(foundedPieceJointePrestation==null) return -1;
pieceJointePrestationDao.delete(foundedPieceJointePrestation);
return 1;
}


public List<PieceJointePrestation> findByCriteria(PieceJointePrestationVo pieceJointePrestationVo){

String query = "SELECT o FROM PieceJointePrestation o where 1=1 ";

            query += SearchUtil.addConstraint( "o", "id","=",pieceJointePrestationVo.getId());
            query += SearchUtil.addConstraint( "o", "path","LIKE",pieceJointePrestationVo.getPath());
        query += SearchUtil.addConstraintDate( "o", "dateAjout","=",pieceJointePrestationVo.getDateAjout());
            query += SearchUtil.addConstraint( "o", "libelle","LIKE",pieceJointePrestationVo.getLibelle());
            query += SearchUtil.addConstraint( "o", "archive","=",pieceJointePrestationVo.getArchive());
        query += SearchUtil.addConstraintDate( "o", "dateArchivage","=",pieceJointePrestationVo.getDateArchivage());
        query += SearchUtil.addConstraintDate( "o", "dateCreation","=",pieceJointePrestationVo.getDateCreation());
            query += SearchUtil.addConstraint( "o", "admin","=",pieceJointePrestationVo.getAdmin());
            query += SearchUtil.addConstraint( "o", "visible","=",pieceJointePrestationVo.getVisible());
            query += SearchUtil.addConstraint( "o", "username","LIKE",pieceJointePrestationVo.getUsername());
            query += SearchUtil.addConstraintMinMaxDate("o","dateAjout",pieceJointePrestationVo.getDateAjoutMin(),pieceJointePrestationVo.getDateAjoutMax());
            query += SearchUtil.addConstraintMinMaxDate("o","dateArchivage",pieceJointePrestationVo.getDateArchivageMin(),pieceJointePrestationVo.getDateArchivageMax());
            query += SearchUtil.addConstraintMinMaxDate("o","dateCreation",pieceJointePrestationVo.getDateCreationMin(),pieceJointePrestationVo.getDateCreationMax());
    if(pieceJointePrestationVo.getPrestationVo()!=null){
        query += SearchUtil.addConstraint( "o", "prestation.id","=",pieceJointePrestationVo.getPrestationVo().getId());
            query += SearchUtil.addConstraint( "o", "prestation.reference","LIKE",pieceJointePrestationVo.getPrestationVo().getReference());
    }

return entityManager.createQuery(query).getResultList();
}

    private void findPrestation(PieceJointePrestation pieceJointePrestation){
        Prestation loadedPrestation =prestationService.findByIdOrReference(pieceJointePrestation.getPrestation());

    if(loadedPrestation==null ) {
    return;
    }
    pieceJointePrestation.setPrestation(loadedPrestation);
    }

@Override
@Transactional
public void delete(List<PieceJointePrestation> pieceJointePrestations){
if(ListUtil.isNotEmpty(pieceJointePrestations)){
pieceJointePrestations.forEach(e->pieceJointePrestationDao.delete(e));
}
}
@Override
public void update(List<PieceJointePrestation> pieceJointePrestations){
if(ListUtil.isNotEmpty(pieceJointePrestations)){
pieceJointePrestations.forEach(e->pieceJointePrestationDao.save(e));
}
}





    }
