package  com.ird.faa.ws.rest.provided.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ird.faa.service.util.*;


import com.ird.faa.bean.PieceJointeEstivage;
import com.ird.faa.ws.rest.provided.vo.PieceJointeEstivageVo;

@Component
public class PieceJointeEstivageConverter extends AbstractConverter<PieceJointeEstivage,PieceJointeEstivageVo>{

        @Autowired
        private EstivageConverter estivageConverter ;
    private Boolean estivage;

public  PieceJointeEstivageConverter(){
init(true);
}

@Override
public PieceJointeEstivage toItem(PieceJointeEstivageVo vo) {
if (vo == null) {
return null;
} else {
PieceJointeEstivage item = new PieceJointeEstivage();
        if(StringUtil.isNotEmpty(vo.getId()))
        item.setId(NumberUtil.toLong(vo.getId()));
        if(StringUtil.isNotEmpty(vo.getPath()))
        item.setPath(vo.getPath());
        if(StringUtil.isNotEmpty(vo.getDateAjout()))
        item.setDateAjout(DateUtil.parse(vo.getDateAjout()));
        if(StringUtil.isNotEmpty(vo.getLibelle()))
        item.setLibelle(vo.getLibelle());
            if(vo.getArchive() != null)
            item.setArchive(vo.getArchive());
        if(StringUtil.isNotEmpty(vo.getDateArchivage()))
        item.setDateArchivage(DateUtil.parse(vo.getDateArchivage()));
        if(StringUtil.isNotEmpty(vo.getDateCreation()))
        item.setDateCreation(DateUtil.parse(vo.getDateCreation()));
            if(vo.getAdmin() != null)
            item.setAdmin(vo.getAdmin());
            if(vo.getVisible() != null)
            item.setVisible(vo.getVisible());
        if(StringUtil.isNotEmpty(vo.getUsername()))
        item.setUsername(vo.getUsername());
    if(vo.getEstivageVo()!=null && this.estivage)
        item.setEstivage(estivageConverter.toItem(vo.getEstivageVo())) ;


return item;
}
}

@Override
public PieceJointeEstivageVo toVo(PieceJointeEstivage item) {
if (item == null) {
return null;
} else {
PieceJointeEstivageVo vo = new PieceJointeEstivageVo();
        if(item.getId()!=null)
        vo.setId(NumberUtil.toString(item.getId()));

        if(StringUtil.isNotEmpty(item.getPath()))
        vo.setPath(item.getPath());

        if(item.getDateAjout()!=null)
        vo.setDateAjout(DateUtil.formateDate(item.getDateAjout()));
        if(StringUtil.isNotEmpty(item.getLibelle()))
        vo.setLibelle(item.getLibelle());

        if(item.getArchive()!=null)
        vo.setArchive(item.getArchive());
        if(item.getDateArchivage()!=null)
        vo.setDateArchivage(DateUtil.formateDate(item.getDateArchivage()));
        if(item.getDateCreation()!=null)
        vo.setDateCreation(DateUtil.formateDate(item.getDateCreation()));
        if(item.getAdmin()!=null)
        vo.setAdmin(item.getAdmin());
        if(item.getVisible()!=null)
        vo.setVisible(item.getVisible());
        if(StringUtil.isNotEmpty(item.getUsername()))
        vo.setUsername(item.getUsername());

    if(item.getEstivage()!=null && this.estivage) {
        vo.setEstivageVo(estivageConverter.toVo(item.getEstivage())) ;
    }

return vo;
}
}

public void init(Boolean value) {
    estivage = value;
}


        public EstivageConverter getEstivageConverter(){
        return this.estivageConverter;
        }
        public void setEstivageConverter(EstivageConverter estivageConverter ){
        this.estivageConverter = estivageConverter;
        }

    public boolean  isEstivage(){
    return this.estivage;
    }
    public void  setEstivage(boolean estivage){
    this.estivage = estivage;
    }






















}
