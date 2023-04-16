import codegenNativeComponent from "react-native/Libraries/Utilities/codegenNativeComponent";
import type { ViewProps } from "react-native";
//import type { Int32 } from 'react-native/Libraries/Types/CodegenTypes';

export type Asset = {
  contentId: string;
  uri: string;
  width: string;
  height: string;
  contentUri: string;
  title:string;
  mediaType: string;
};

export type SectionDataSource={
  sectionId: string;
  sectionTitle: string;
  data:Array<Asset>;
}
interface NativeProps extends ViewProps {
  color?: string;
  dataSourceString?: string;
  dataSource?: Array<Asset>;
}

export default codegenNativeComponent<NativeProps>("RecyclerviewAndroidView");
