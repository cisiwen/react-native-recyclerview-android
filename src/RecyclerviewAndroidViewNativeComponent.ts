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

export type SectionHeaderStyle = {
  BackgroudColor:string;
  Padding:number;
  FontSize:number;
  FontColor:string;
  FontWeight:number;
}


interface NativeProps extends ViewProps {
  color?: string;
  sectionHeaderStyle?: string;
  dataSourceString?: string;
  dataSource?: Array<Asset>;
  onLongPressed: DirectEventHandler<OnPageSelectedEventData>;
  onItemPressed: DirectEventHandler<OnPageSelectedEventData>;
  OnItemSelectStateChanged: DirectEventHandler<OnPageSelectedEventData>;
}

export default codegenNativeComponent<NativeProps>("GalleryListView");
//export const ZoomableImageView = codegenNativeComponent<NativeProps>("ZoomableImageView");
