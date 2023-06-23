import { PageContainer } from '@ant-design/pro-components';
import React, { useEffect, useState } from 'react';
import {Button, Card, Descriptions, Divider, Form, Input, message, Image, Badge} from 'antd';
import {
  getInterfaceInfoByIdUsingGET,
  invokeInterfaceInfoUsingPOST,
} from '@/services/easyapi-backend/interfaceInfoController';
import { useParams } from '@@/exports';
import moment from "moment";
import ReactJson from 'react-json-view'
import VanillaJSONEditor from "./VanillaJSONEditor";

/**
 * 查看接口文档
 * @constructor
 */
const Index: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<API.InterfaceInfo>();
  const [invokeRes, setInvokeRes] = useState<any>()
  const [invokeLoading, setInvokeLoading] = useState(false);
  const params = useParams();
  const param=new Object();
  const [img, setImg] = useState(null);
  const [content, setContent] = useState({
    json: param,
    text: undefined
  });


  const loadData = async () => {
    if (!params.id) {
      message.error('参数不存在');
      return;
    }
    //百度接口
    if(Number(params.id)===2){
      param.size="10";
    }
    //毒鸡汤接口
    if(Number(params.id)===3){
      param.charset="utf-8";
      param.encode="text";
    }
    //域名接口
    if(Number(params.id)===4){
      param.domain="";
    }
    if(Number(params.id)===5){
      param.qq="";
    }
    //用户名接口
    if(Number(params.id)===6){
      param.username="zhangsan";
    }
    setLoading(true);
    try {
      const res = await getInterfaceInfoByIdUsingGET({
        id: Number(params.id),
      });
      setData(res.data);
    } catch (error: any) {
      message.error('请求失败' + error.message);
    }
    setLoading(false);
  };

  useEffect(() => {
    loadData();
  }, []);

  const onFinish = async (values: any) => {
    if (!params.id) {
      message.error('接口不存在');
      return;
    }
    let jsonValue= JSON.stringify(content.json);
    // console.log("jsonvalue" + jsonValue)
    console.log(jsonValue);
    console.log(typeof jsonValue);
    //let replaceAfter;
    if(jsonValue== undefined || jsonValue == null || jsonValue === ''){
      jsonValue=JSON.parse(JSON.stringify(content.text));

    }
    setInvokeLoading(true);
    try {
      const res = await invokeInterfaceInfoUsingPOST({
        id: Number(params.id),
        userRequestParams:jsonValue
      });
      let json1;
      console.log(res);
      if(res.code===40100){
        json1=res;
        message.error("登录过期，请重新登录！")
      }
      if(params.id==='1'){
        setImg(res.data);
      }else {
        json1=JSON.parse(res.data);
      }
      console.log(typeof json1)
      console.log(json1)
      setInvokeRes(json1);


      message.success('请求成功');
    } catch (error: any) {
      message.error('操作失败，' + error.message);
    }
    setInvokeLoading(false);
  };

  const onFinishFailed = (errorInfo: any) => {
    console.log('Failed:', errorInfo);
  };

  return (
    <PageContainer title={'查看接口文档'}>
      <Card>
        {data ? (
          <Descriptions title={data.name} bordered>
            <Descriptions.Item label="描述" span={2}>{data.description}</Descriptions.Item>
            <Descriptions.Item label="请求地址">{data.url}</Descriptions.Item>
            <Descriptions.Item label="请求方法">{data.method}</Descriptions.Item>
            <Descriptions.Item label="创建时间">{moment(data.createTime).format('YYYY-MM-DD HH:mm:ss')}</Descriptions.Item>
            <Descriptions.Item label="更新时间" span={2}>
              {moment(data.updateTime).format('YYYY-MM-DD HH:mm:ss')}
            </Descriptions.Item>
            <Descriptions.Item label="接口状态" span={3}>
              <Badge status={data.status===1?"success":"error"} text={data.status===1?"正常":"下线"} />
            </Descriptions.Item>
            <Descriptions.Item label="请求头">{data.requestHeader}</Descriptions.Item>
            <Descriptions.Item label="响应头" span={2}>{data.responseHeader}</Descriptions.Item>
            <Descriptions.Item label="请求参数" span={3}>
              <ReactJson name={false} src={JSON.parse(data.requestParams)} />
            </Descriptions.Item>
          </Descriptions>
        ) : (
          <>接口不存在</>
        )}
      </Card>
      <Divider/>
      <Card title="在线测试">
        <Form
          name="invoke"
          layout={"vertical"}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
        >
          <Form.Item
            label="请求参数"
            name="userRequestParams"
          >
            <div className="my-editor">
              <VanillaJSONEditor
                content={content}
                onChange={setContent}
              />
            </div>
            <Input.TextArea />
          </Form.Item>

          <Form.Item wrapperCol={{ span: 16 }}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Divider/>
      <Card title={"返回结果"} loading={invokeLoading}>
        { img  ?  <Image
          width={200}
          src={img}
        />:<ReactJson name={false} src={invokeRes} />}
      </Card>
    </PageContainer>
  );
};

export default Index;
