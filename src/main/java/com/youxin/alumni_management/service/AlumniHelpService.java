package com.youxin.alumni_management.service;

import com.youxin.alumni_management.pojo.AlumniHelp;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description
 * @date 2022-04-29 15:39
 */
public interface AlumniHelpService {

    List<AlumniHelp> findAllAlumniHelp();

    //插入校友帮扶
    int insAlumniHelp(AlumniHelp alumniHelp);

    AlumniHelp selAlumniHelpById(Integer helpId);

    List<AlumniHelp> findTop2AlumniHelp();

    List<AlumniHelp> findAllAlumniHelpByDepartmentId(Integer departmentId);
}
