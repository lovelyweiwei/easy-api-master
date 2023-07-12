import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import '@umijs/max';
const Footer: React.FC = () => {
  const defaultMessage = 'zw出品';
  const currentYear = new Date().getFullYear();
  const beian = '湘ICP备2023016227号-1';
  const beianUrl = 'https://beian.miit.gov.cn/#/Integrated/index';
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={
        <>
          {currentYear} {defaultMessage} | {' '}
          <a href={beianUrl} target="_blank" rel="noreferrer">
            {beian}
          </a>
        </>
      }
      links={[
        {
          key: 'Easy API',
          title: 'Easy API',
          href: 'https://github.com/lovelyweiwei/easy-api-master',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/lovelyweiwei/easy-api-master',
          blankTarget: true,
        },
        {
          key: '简单 API ',
          title: '简单 API',
          href: 'https://gitee.com/weiweibiubiu/easy-api-master',
          blankTarget: true,
        },
      ]}
    >
      <span>
        <a href="http://www.beian.miit.gov.cn/" target="_blank" rel="noopener noreferrer">
          ICP备12345678号
        </a>
      </span>
    </DefaultFooter>
  );
};
export default Footer;
