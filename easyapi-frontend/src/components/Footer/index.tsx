import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import '@umijs/max';
const Footer: React.FC = () => {
  const defaultMessage = 'zw';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Easy API',
          title: 'Easy API',
          href: 'https://gitee.com/weiweibiubiu/easy-api-master',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/ant-design/ant-design-pro',
          blankTarget: true,
        },
        {
          key: '简单 API ',
          title: '简单 API',
          href: 'https://github.com/lovelyweiwei/easy-api-master',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
