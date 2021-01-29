package kh.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.spring.dao.ProFileDAO;
import kh.spring.dao.ProfessorDAO;
import kh.spring.dto.DepartmentDTO;
import kh.spring.dto.DepartmentOfficeDTO;
import kh.spring.dto.MilitaryDTO;
import kh.spring.dto.ProAttendMngDTO;
import kh.spring.dto.ProFileDTO;
import kh.spring.dto.ProListDTO;
import kh.spring.dto.ProScheduleDTO;
import kh.spring.dto.ProScheduleDTO_NEX;
import kh.spring.dto.ProfessorDTO;
import kh.spring.dto.ProfessorDTO_NEX;
import kh.spring.utils.ConvertDate;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorDAO pdao;
	
	@Autowired
	private ProFileDAO fdao;
	
	private ConvertDate con;
	
	
	public List<ProfessorDTO> selectInfo(String id){		
		return pdao.selectProInfo(id);
	}
	
	public List<MilitaryDTO> selectMil(String id){
		return pdao.selectMil(id);		
	}
	
	public int updateProInfo(ProfessorDTO_NEX dto) {
		ProfessorDTO pdto = new ProfessorDTO(dto.getId(),dto.getPw(),dto.getName(),con.utilToSql(dto.getBirth()),
				dto.getGender(),dto.getCountry(),con.utilToSql(dto.getInDate()),con.utilToSql(dto.getOutDate()),
				dto.getCol_title(),dto.getDept_title(),dto.getZipcode(),dto.getAddr1(),dto.getAddr2(),dto.getEmail(),
				dto.getPhone(),dto.getBank(),dto.getAccountnum());
		return pdao.updateProInfo(pdto);
	}
	
	public ProFileDTO checkImg(String id) {
		return fdao.checkImg(id);
	}
	
	public List<ProListDTO> selectProList(String id){
		return pdao.selectProList(id);
	}

	public DepartmentDTO selectDepartment(String id) {
		return pdao.selectDepartment(id);
	}
	
	public DepartmentOfficeDTO selectDepartmentOffice(String id) {
		return pdao.selectDepartmentOffice(id);
	}
	public List<ProAttendMngDTO> proAttendMngOnload(String id){	
		return pdao.selectProAttendMngOnload(id);
	}
	public List<ProScheduleDTO> proScheduleOnload(String id){
		return pdao.selectProSchedule(id);
	}
	public int delProSchedule(List<ProScheduleDTO_NEX> list) {
		List<ProScheduleDTO> plist = new ArrayList<>();
		for(ProScheduleDTO_NEX i : list) {
			ProScheduleDTO dto = new ProScheduleDTO();
			dto.setCheck(i.getCheck());
			dto.setSche_contents(i.getSche_contents());
			dto.setSche_date(ConvertDate.utilToSql(i.getSche_date()));
			dto.setSche_proId(i.getSche_proId());
			dto.setSche_proName(i.getSche_proName());
			dto.setSche_seq(i.getSche_seq());
			dto.setSche_title(i.getSche_title());
			dto.setSche_type(i.getSche_type());
			plist.add(dto);
		}
		return pdao.delProSchedule(plist);
	}
	public int updateProSchedule(ProScheduleDTO_NEX dto, String id) {
		ProScheduleDTO sDto = new ProScheduleDTO(dto.getSche_seq(),dto.getSche_proId(),dto.getSche_proName(),dto.getSche_contents(),
				ConvertDate.utilToSql(dto.getSche_date()),dto.getSche_title(),dto.getSche_type(),dto.getCheck());
		int result = 0;
		if(id.contentEquals("insert")) {
			result = pdao.insertProSchedule(sDto);
		}else if(id.contentEquals("update")) {
			result = pdao.updateProSchedule(sDto);
		}
		return result;
	}
}