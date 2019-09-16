package com.conpany.project.web;



import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import testBase.web.BaseControllerTest;
import testBase.web.WithCustomUser;

/**
 * 账户接口测试
 *
 * @author Zoctan
 * @date 2018/11/29
 */
//按NAME_ASCENDING进行排序执行
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountControllerTest extends BaseControllerTest {

  private final String resource = "/account";


  /** detail */
  @Test(timeout = 5000)
  @WithCustomUser(name = "xxxxx")
  public void test5() throws Exception {
    final String targetUrl = this.resource + "/1";
    this.get(targetUrl, null, null);
  }

  /** list */
  @Test(timeout = 5000)
  @WithCustomUser(name = "user")
  public void test6() throws Exception {
    final String targetUrl = this.resource + "?page=1&size=3";
    this.get(targetUrl, null, null);
  }

}
