package com.youxin.alumni_management.service.impl;

import com.youxin.alumni_management.mapper.AlumniHelpMapper;
import com.youxin.alumni_management.pojo.AlumniHelp;
import com.youxin.alumni_management.service.AlumniHelpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youxin
 * @program alumni_management
 * @description 帮扶文章服务实现类
 * @date 2022-04-29 15:40
 */
@Service
@RequiredArgsConstructor
public class AlumniHelpServiceImpl implements AlumniHelpService {

    private final AlumniHelpMapper alumniHelpMapper;

    @Override
    public List<AlumniHelp> findAllAlumniHelp() {
        return alumniHelpMapper.findAllAlumniHelp();
    }

    @Override
    public int insAlumniHelp(AlumniHelp alumniHelp) {
        return alumniHelpMapper.insAlumniHelp(alumniHelp);
    }

    @Override
    public AlumniHelp selAlumniHelpById(Integer helpId) {
        return alumniHelpMapper.selAlumniHelpById(helpId);
    }

    @Override
    public List<AlumniHelp> findTop2AlumniHelp() {
        return alumniHelpMapper.findTop2AlumniHelp();
    }
}
