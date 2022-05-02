package com.youxin.alumni_management.mapper;

import com.youxin.alumni_management.pojo.AlumniHelp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 校友帮扶mapper层
 * @date 2022-04-29 15:29
 */
@Mapper
public interface AlumniHelpMapper {

    List<AlumniHelp> findAllAlumniHelp();

    int insAlumniHelp(AlumniHelp alumniHelp);

    AlumniHelp selAlumniHelpById(Integer helpId);
}
