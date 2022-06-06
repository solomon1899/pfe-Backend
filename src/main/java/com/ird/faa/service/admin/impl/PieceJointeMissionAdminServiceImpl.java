package com.ird.faa.service.admin.impl;

import java.util.List;
    import java.util.Date;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import com.ird.faa.bean.PieceJointeMission;
        import com.ird.faa.bean.Mission;
import com.ird.faa.dao.PieceJointeMissionDao;
import com.ird.faa.service.admin.facade.PieceJointeMissionAdminService;
        import com.ird.faa.service.admin.facade.MissionAdminService;

import com.ird.faa.ws.rest.provided.vo.PieceJointeMissionVo;
import com.ird.faa.service.util.*;

    import com.ird.faa.service.core.facade.ArchivableService;
import com.ird.faa.service.core.impl.AbstractServiceImpl;

@Service
public class PieceJointeMissionAdminServiceImpl extends AbstractServiceImpl<PieceJointeMission> implements PieceJointeMissionAdminService {

@Autowired
private PieceJointeMissionDao pieceJointeMissionDao;

    @Autowired
    private ArchivableService<PieceJointeMission> archivableService;
        @Autowired
        private MissionAdminService missionService ;


@Autowired
private EntityManager entityManager;


@Override
public List<PieceJointeMission> findAll(){
        return pieceJointeMissionDao.findAll();
}

        @Override
        public List<PieceJointeMission> findByMissionReference(String reference){
        return pieceJointeMissionDao.findByMissionReference(reference);
        }

        @Override
        @Transactional
        public int deleteByMissionReference(String reference){
        return pieceJointeMissionDao.deleteByMissionReference(reference);
        }

        @Override
        public List<PieceJointeMission> findByMissionId(Long id){
        return pieceJointeMissionDao.findByMissionId(id);
        }

        @Override
        @Transactional
        public int deleteByMissionId(Long id){
        return pieceJointeMissionDao.deleteByMissionId(id);
        }


@Override
public PieceJointeMission findById(Long id){
if(id==null) return null;
return pieceJointeMissionDao.getOne(id);
}

@Override
public PieceJointeMission findByIdWithAssociatedList(Long id){
    return findById(id);
}
    @Override
    public PieceJointeMission archiver(PieceJointeMission pieceJointeMission) {
    if (pieceJointeMission.getArchive() == null) {
    pieceJointeMission.setArchive(false);
    }
    pieceJointeMission.setArchive(true);
    pieceJointeMission.setDateArchivage(new Date());
    pieceJointeMissionDao.save(pieceJointeMission);
    return pieceJointeMission;

    }

    @Override
    public PieceJointeMission desarchiver(PieceJointeMission pieceJointeMission) {
    if (pieceJointeMission.getArchive() == null) {
    pieceJointeMission.setArchive(false);
    }
    pieceJointeMission.setArchive(false);
    pieceJointeMission.setDateArchivage(null);
    pieceJointeMissionDao.save(pieceJointeMission);
    return pieceJointeMission;
    }



@Transactional
public int deleteById(Long id){
int res=0;
if(pieceJointeMissionDao.findById(id).isPresent())  {
pieceJointeMissionDao.deleteById(id);
res = 1;
}
return res;
}


@Override
public PieceJointeMission update(PieceJointeMission pieceJointeMission){
PieceJointeMission foundedPieceJointeMission = findById(pieceJointeMission.getId());
if(foundedPieceJointeMission==null) return null;
else{
    archivableService.prepare(pieceJointeMission);
return  pieceJointeMissionDao.save(pieceJointeMission);
}
}
    private void prepareSave(PieceJointeMission pieceJointeMission){
        pieceJointeMission.setDateCreation(new Date());
                    if(pieceJointeMission.getArchive() == null)
                pieceJointeMission.setArchive(false);
                    if(pieceJointeMission.getAdmin() == null)
                pieceJointeMission.setAdmin(false);
                    if(pieceJointeMission.getVisible() == null)
                pieceJointeMission.setVisible(false);



    }

@Override
public PieceJointeMission save (PieceJointeMission pieceJointeMission){
    prepareSave(pieceJointeMission);



    findMission(pieceJointeMission);

    return pieceJointeMissionDao.save(pieceJointeMission);


}

@Override
public List<PieceJointeMission> save(List<PieceJointeMission> pieceJointeMissions){
List<PieceJointeMission> list = new ArrayList<>();
for(PieceJointeMission pieceJointeMission: pieceJointeMissions){
list.add(save(pieceJointeMission));
}
return list;
}



@Override
@Transactional
public int delete(PieceJointeMission pieceJointeMission){
    if(pieceJointeMission.getId()==null) return -1;
    PieceJointeMission foundedPieceJointeMission = findById(pieceJointeMission.getId());
    if(foundedPieceJointeMission==null) return -1;
pieceJointeMissionDao.delete(foundedPieceJointeMission);
return 1;
}


public List<PieceJointeMission> findByCriteria(PieceJointeMissionVo pieceJointeMissionVo){

String query = "SELECT o FROM PieceJointeMission o where 1=1 ";

            query += SearchUtil.addConstraint( "o", "id","=",pieceJointeMissionVo.getId());
            query += SearchUtil.addConstraint( "o", "path","LIKE",pieceJointeMissionVo.getPath());
        query += SearchUtil.addConstraintDate( "o", "dateAjout","=",pieceJointeMissionVo.getDateAjout());
            query += SearchUtil.addConstraint( "o", "libelle","LIKE",pieceJointeMissionVo.getLibelle());
            query += SearchUtil.addConstraint( "o", "archive","=",pieceJointeMissionVo.getArchive());
        query += SearchUtil.addConstraintDate( "o", "dateArchivage","=",pieceJointeMissionVo.getDateArchivage());
        query += SearchUtil.addConstraintDate( "o", "dateCreation","=",pieceJointeMissionVo.getDateCreation());
            query += SearchUtil.addConstraint( "o", "admin","=",pieceJointeMissionVo.getAdmin());
            query += SearchUtil.addConstraint( "o", "visible","=",pieceJointeMissionVo.getVisible());
            query += SearchUtil.addConstraint( "o", "username","LIKE",pieceJointeMissionVo.getUsername());
            query += SearchUtil.addConstraintMinMaxDate("o","dateAjout",pieceJointeMissionVo.getDateAjoutMin(),pieceJointeMissionVo.getDateAjoutMax());
            query += SearchUtil.addConstraintMinMaxDate("o","dateArchivage",pieceJointeMissionVo.getDateArchivageMin(),pieceJointeMissionVo.getDateArchivageMax());
            query += SearchUtil.addConstraintMinMaxDate("o","dateCreation",pieceJointeMissionVo.getDateCreationMin(),pieceJointeMissionVo.getDateCreationMax());
    if(pieceJointeMissionVo.getMissionVo()!=null){
        query += SearchUtil.addConstraint( "o", "mission.id","=",pieceJointeMissionVo.getMissionVo().getId());
            query += SearchUtil.addConstraint( "o", "mission.reference","LIKE",pieceJointeMissionVo.getMissionVo().getReference());
    }

return entityManager.createQuery(query).getResultList();
}

    private void findMission(PieceJointeMission pieceJointeMission){
        Mission loadedMission =missionService.findByIdOrReference(pieceJointeMission.getMission());

    if(loadedMission==null ) {
    return;
    }
    pieceJointeMission.setMission(loadedMission);
    }

@Override
@Transactional
public void delete(List<PieceJointeMission> pieceJointeMissions){
if(ListUtil.isNotEmpty(pieceJointeMissions)){
pieceJointeMissions.forEach(e->pieceJointeMissionDao.delete(e));
}
}
@Override
public void update(List<PieceJointeMission> pieceJointeMissions){
if(ListUtil.isNotEmpty(pieceJointeMissions)){
pieceJointeMissions.forEach(e->pieceJointeMissionDao.save(e));
}
}





    }
