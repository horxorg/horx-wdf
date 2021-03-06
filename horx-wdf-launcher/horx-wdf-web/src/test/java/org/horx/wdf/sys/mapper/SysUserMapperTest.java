package org.horx.wdf.sys.mapper;

import org.horx.wdf.common.entity.extension.EntityExtension;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath*:spring.xml"})
public class SysUserMapperTest {

    @Autowired
    private UserMapper sysUserMapper;

    @Autowired
    private EntityExtension entityExtension;

    /*@Test
    public void testInsert() {
        SysUserExt user = new SysUserExt();
        //user.setId(1L);
        user.setName("abc1");
        user.setPwdSalt("abcdefgh");
        user.setExt1("aa");
        user.setExt2("12");
        sysUserMapper.insert(user);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdate() {
        User sysUser =  new User();
        sysUser.setId(36L);
        sysUser.setName("1234u");

        sysUserMapper.update(sysUser);
    }

    @Test
    public void testUpdateEnhanced() {
        User sysUser = entityExtension.newEntity(User.class, true);
        SysUserExt sysUserExt = (SysUserExt)sysUser;
        sysUserExt.setId(36L);
        sysUserExt.setName("1234ue");
        sysUserExt.setExt1(null);

        sysUserMapper.update(sysUser);
    }

    @Test
    public void testDelById() {
        sysUserMapper.deleteById(35L);
    }

    @Test
    public void testSelectById() {
        User sysUser = sysUserMapper.selectById(37L);
        System.out.println(sysUser.getPwdSalt());
        System.out.println(((SysUserExt)sysUser).getExt2());
        System.out.println(sysUser.getClass());
    }

    @Test
    public void testFindForPage() {
        UserQueryDTO userQuery = new UserQueryDTO();
        PaginationParam paginationParam = new PaginationParam();
        paginationParam.setPageSize(10);
        paginationParam.setCurrPage(1);

        PaginationResult<User> paginationResult = sysUserMapper.paginationSelect(userQuery, paginationParam);
        System.out.println(paginationResult.getTotal());
        System.out.println(JsonUtils.toJson(paginationResult));

        //List<SysUser> paginationResult = sysUserMapper.findForPage(param, paginationParam);
        //System.out.println(paginationParam.getCount());
        //System.out.println(paginationResult.size());
    }*/
}
