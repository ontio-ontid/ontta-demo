package com.onchain.tademo;

import com.onchain.tademo.model.SensetimeDto;
import com.onchain.tademo.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TademoApplicationTests {

    @Autowired
    AuthenticationService authenticationService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void example01(){

        // 1、构造参数
        SensetimeDto senseTimeDto = new SensetimeDto();
        senseTimeDto.setId(1);
        senseTimeDto.setIdNumber("340123190000112233");
        senseTimeDto.setName("李李李");
        senseTimeDto.setArOntid("did:ont:AHzg7vYANf6XHnBL64hT3vorzj4v7sZpt3");
        senseTimeDto.setOwnerOntid("did:ont:AHzg7vYANf6XHnBL64hT3vorzj4v7sZpt4");
        senseTimeDto.setAuthId("");
        senseTimeDto.setCreateTime(new Date());

        // 2、认证
        authenticationService.hanldeSenseTimeVerifySucceedResult(senseTimeDto);
    }

    // 解析claim
    @Test
    public void example02(){

        String claim = "eyJraWQiOiJkaWQ6b250OkFlTms2cjFjaTJ2Q0xXbTNaQ1JvdFU3RzF6U3pveTVtRk4ja2V5cy0xIiwidHlwIjoiSldULVgiLCJhbGciOiJPTlQtRVMyNTYifQ==" +
                ".eyJjbG0tcmV2Ijp7InR5cCI6IkF0dGVzdENvbnRyYWN0IiwiYWRkciI6IjM2YmI1YzA1M2I2YjgzOWM4ZjZiOTIzZmU4NTJmOTEyMzliOWZjY2MifSwic3ViIjoiZGlkOm9udDpBSHpnN3ZZQU5mNlhIbkJMNjRoVDN2b3J6ajR2N3NacHQ0IiwidmVyIjoidjEuMCIsImNsbSI6eyLouqvku73or4Hlj7ciOiIzNDAxMjMxOTAwMDAxMTIyMzMiLCLlp5PlkI0iOiLmnY7mnY7mnY4iLCJJc3N1ZXJOYW1lIjoiU2Vuc2V0aW1lIn0sImlzcyI6ImRpZDpvbnQ6QWVOazZyMWNpMnZDTFdtM1pDUm90VTdHMXpTem95NW1GTiIsImV4cCI6MTU5MzY3MzY3MywiaWF0IjoxNTYyMDUxMjgwLCJAY29udGV4dCI6ImNsYWltOnNlbnNldGltZV9hdXRoZW50aWNhdGlvbiIsImp0aSI6IjI1NzUwYzg4ZjdlZTZkNzIwNGUwMDQxN2YwNzIzY2M2ZTlhZWE2MDE0N2VkYmYzYzA3MmVhNzAwODgzMzQ3MGEifQ==.AWjmQ/tQCCF+j1e6n5iNP8Xn1Lc4TzRyzNfwnCirWn7guqckodyfdsEOq7a3fRw1LxX9LjbCkEA+cdvQxqnklFg=\\.eyJUeXBlIjoiTWVya2xlUHJvb2YiLCJNZXJrbGVSb290IjoiN2EzMDAxMWJhODY4OGU2YzE2YTM5NGU3YzQ5ODAxYWJkNTYwNGRmOWFhNTg4NjViYTAxNjIxMmFhNDcwNmMzNSIsIlR4bkhhc2giOiIwMjNmM2Y1NzI3MWFlNmNkZWViYWI2MzUyYzAwYmQxMmNmMGQ1YjYzMjE3MTE1ZjdkZjEzMDhhY2EyNjVlMmRjIiwiQmxvY2tIZWlnaHQiOjIwNTM1ODMsIk5vZGVzIjpbeyJUYXJnZXRIYXNoIjoiMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMCIsIkRpcmVjdGlvbiI6IkxlZnQifSx7IlRhcmdldEhhc2giOiI3N2M1ZGJmYmNhMjdjY2RlY2U3NjVlYzA1ODM2YzU2MWE1NzFmMDE4YmZkZWVkNzhiN2QyYWVlY2QwOTgwN2FlIiwiRGlyZWN0aW9uIjoiTGVmdCJ9LHsiVGFyZ2V0SGFzaCI6IjFhOTVjMDg2ZjIyYWVjNzE0YzgwNmEwZmUzNTNiY2Y4MTNkNmY4OGM2MTc3ZDMwZjE2YzQ5YmUyODVjMjc4MjUiLCJEaXJlY3Rpb24iOiJMZWZ0In0seyJUYXJnZXRIYXNoIjoiZjQ0MDUzMTk5OWM1NDdkYjA4ZjUxNjY3N2MxNTIyMTU0NzVhNjlkY2NiODIxNzZlNGJjYTFiNzI2MjYxYTFiZSIsIkRpcmVjdGlvbiI6IkxlZnQifSx7IlRhcmdldEhhc2giOiIzMTA0MjQwZTkwMDA5OGIxYThjODIzNzVhNTA2NzE2N2ZkMWU2OTdjNDZkZjVhZGE5Y2E2YjYxZGNjYThmMWU2IiwiRGlyZWN0aW9uIjoiTGVmdCJ9LHsiVGFyZ2V0SGFzaCI6IjY2MzYzYzEzNjc0ZTFlOGU3MjUyZjMxMGQzNjBmODAxNzI4MjFhNzE4YjM4YWNkZTk0NzQ3YjdhMTkzMjhiNzQiLCJEaXJlY3Rpb24iOiJMZWZ0In0seyJUYXJnZXRIYXNoIjoiMmEzMTRlZWQwODljN2Y1ZmZiMzMyZGVkNjYwNmMxYWFhYzg0NzU1ODc5NTg3MmQzMzFhN2M3NWU4MDg2NjFiYSIsIkRpcmVjdGlvbiI6IkxlZnQifSx7IlRhcmdldEhhc2giOiIzZGNkMjNkYTc5OTM4OTk1NDNjMGFhMmFiOTRiNjUyMmEzZGNjMjIyMjU5OGM2MmMzMjU4ZTE5MTc0NDYzYTA4IiwiRGlyZWN0aW9uIjoiTGVmdCJ9LHsiVGFyZ2V0SGFzaCI6IjM2MjZjZTUyMDVlY2FmZTA5ZWU2YzE5ZmM3NTIxNGZiZjA5NzdiNjkzMmU2OTExNWI4NjYyMWQ1NWQ3ZjFlZTQiLCJEaXJlY3Rpb24iOiJMZWZ0In0seyJUYXJnZXRIYXNoIjoiZThkNzcyOWFhY2YwMDIxOWY4MGE3YjQ2YjdkOGMyMzljYTQ5ODM4OThmNTVhZmVkODdkNDcwMDNmNTNiYWM3YiIsIkRpcmVjdGlvbiI6IkxlZnQifSx7IlRhcmdldEhhc2giOiJhZDhlYmViNmU2YmUxZDFiZmE5ZjgzYzBmMmU0OTBhMzAxMDhiOGRhYTZiYWQ2ODBkZWU0MTg0NzQ2ODA1Yjc4IiwiRGlyZWN0aW9uIjoiTGVmdCJ9LHsiVGFyZ2V0SGFzaCI6IjI2NjIwNzdlMzQ5MzVkNTQ5NTQ3OWQwNmU3NzNiYzIzN2NmZmExNDkzMGI2NDRiMzI1NGY1NGZmMGUxY2U4ZDciLCJEaXJlY3Rpb24iOiJMZWZ0In0seyJUYXJnZXRIYXNoIjoiY2RiYzc1OTA1YTJhYTYwODYwYjA4MDEwZjk3Y2NiMzI4M2Q3OTNmNTM1ZjNkYTlmYzE2MzAyOGY1ZDc2YjZhNCIsIkRpcmVjdGlvbiI6IkxlZnQifSx7IlRhcmdldEhhc2giOiJiNTgzOTZlMjE5MGM1ZTRmYTc2MjRlNjA4OTczMTI3YmNhNTY3Nzg2ZjA5YzFhOWFkNTQ4NjdhZGFjZmM5MjlkIiwiRGlyZWN0aW9uIjoiTGVmdCJ9LHsiVGFyZ2V0SGFzaCI6ImJiMmVjZDZiOTZkNWQzOTUwOTdhZGE1ZWJhY2E4NTNkZjlhNGM0YmU1ODYwMWU3OWI0OTY4NzJhOTc3NjU1YTciLCJEaXJlY3Rpb24iOiJMZWZ0In1dLCJDb250cmFjdEFkZHIiOiIzNmJiNWMwNTNiNmI4MzljOGY2YjkyM2ZlODUyZjkxMjM5YjlmY2NjIn0=";

        String[] datas = claim.split("\\.");
        System.out.println("length:" + datas.length);

        String head = new String(Base64.getDecoder().decode(datas[0]));
        System.out.println("head:" + head);

        String payload = new String(Base64.getDecoder().decode(datas[1]));
        System.out.println("payload:" + payload);

        String signature = datas[2];
        System.out.println("signature:"+signature);

        String merkleproof = new String(Base64.getDecoder().decode(datas[3]));
        System.out.println("merkleproof:" + merkleproof);

    }
}
