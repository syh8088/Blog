package kiwi.blog.resources.service;

import kiwi.blog.resources.model.entity.Resource;
import kiwi.blog.resources.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    @Autowired
    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();

        List<Resource> resourcesList = resourceRepository.findAllResources();

        resourcesList.forEach(resource -> {

            List<ConfigAttribute> configAttributeList =  new ArrayList<>();

            resource.getRoleSet().forEach(role -> {
                configAttributeList.add(new SecurityConfig(role.getName()));
            });

            result.put(new AntPathRequestMatcher(resource.getName()), configAttributeList);
        });

        return result;
    }
}
