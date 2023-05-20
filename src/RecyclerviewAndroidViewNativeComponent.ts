import codegenNativeComponent from "react-native/Libraries/Utilities/codegenNativeComponent";
import type { ViewProps } from "react-native";
import type * as ReactNative from 'react-native';
import type { DirectEventHandler, Int32 } from "react-native/Libraries/Types/CodegenTypes";
type OnPageSelectedEventData = Readonly<{
  position: Int32;
}>;
export type PagerViewOnPageSelectedEventData = OnPageSelectedEventData;
export type PagerViewOnPageSelectedEvent =ReactNative.NativeSyntheticEvent<PagerViewOnPageSelectedEventData>;
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
  onLongPressed: DirectEventHandler<OnPageSelectedEventData>;
}

export default codegenNativeComponent<NativeProps>("GalleryListView");
