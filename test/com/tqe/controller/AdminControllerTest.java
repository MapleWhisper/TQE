package com.tqe.controller;

import com.tqe.base.BaseResult;
import com.tqe.base.BaseTest;
import com.tqe.po.Admin;
import com.tqe.service.AdminServiceImpl;
import org.junit.Assert;
import static  org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;

import java.util.List;

/**
 * Created by Maple on 2016/5/23.
 */
public class AdminControllerTest extends BaseTest {

    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    AdminController adminController;
    @Test
    public void testGetAdminById() throws Exception {
        BaseResult baseResult = adminController.getAdminById(1);
        assertTrue(baseResult.isSuccess());
        Admin admin = (Admin) baseResult.getItem();
        assertNotNull(admin);
        assertEquals(admin.getName(),"admin");
    }

    @Test
    public void testAdmin() throws Exception {

    }



    @Test
    public void testSave() throws Exception {
        //save
        Admin admin = new Admin();
        admin.setUsername(TEST);
        admin.setPassword(TEST);
        admin.setPosition(TEST);
        admin.setName(TEST);
        adminController.save(admin, new ExtendedModelMap());

        List<Admin> adminList = adminService.findAll();
        boolean find = false;

        for(Admin a : adminList){
            if(a.getUsername().equals(TEST)){
                find = true;
                admin = a;
                break;
            }
        }
        assertTrue(find);
    }

    @Test
    public void testUpdate() throws Exception {
        //in save
    }

    @Test
    public void testDelete() throws Exception {
        // in save
    }
}