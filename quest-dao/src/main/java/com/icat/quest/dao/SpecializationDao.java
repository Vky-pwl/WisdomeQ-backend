/**
 * 
 */
package com.icat.quest.dao;

import com.icat.quest.generic.dao.framework.GenericDao;
import com.icat.quest.model.Specialization;

/**
 * @author satyendra
 *
 */
public interface SpecializationDao extends GenericDao<Specialization,Integer>{

		
	public String findAllBySearchKeyWithFilter="from com.icat.quest.model.Specialization specialization where "
			+ "specialization.active =:_1_active and specialization.specializationName like :_2_specializationName order by specialization.specializationId desc";
	public String findAllByFilter="from com.icat.quest.model.Specialization specialization where specialization.active =:_1_active order by specialization.specializationId desc";
	public String findAllBySearchKey="from com.icat.quest.model.Specialization specialization where specialization.specializationName like :_2_specializationName order by specialization.specializationId desc";
	public String findAll = "from com.icat.quest.model.Specialization specialization order by specialization.specializationId desc";

	
	
}
