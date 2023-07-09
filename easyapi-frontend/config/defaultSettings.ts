import { Settings as LayoutSettings } from '@ant-design/pro-components';

/**
 * @name
 */
const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  colorPrimary: '#1890ff',
  // colorPrimary: "#1677FF",
  layout: 'mix',
  // layout: 'top',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  splitMenus: false,
  title: 'EasyApi开放平台',
  pwa: false,
  logo: 'http://139.9.104.196/logo.svg',
  iconfontUrl: '',
};

export default Settings;
