# ontta-demo

以商汤认证为例，展示如何签发claim。

# 准备

配置文件为`application.properties`

1、认证方（例如商汤）的ONTID，包括ONTID、密码、slat；
```properties
sensetime.identity.ontId=
sensetime.identity.password=
sensetime.identity.salt=
```

2、申请付款账户地址，并充值，测试网的充值地址是：https://developer.ont.io/applyOng
```properties
account.address=
account.wif=
```

3、配置区块链地址、合约地址等信息；
```properties
ontology.restfulUrl=http://polaris1.ont.io:20334
claimRecord.codeHash=36bb5c053b6b839c8f6b923fe852f91239b9fccc
```


# 构造参数

```java
// TademoApplicationTests.java
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
```

# 解析claim
```java
// TademoApplicationTests.java
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
```