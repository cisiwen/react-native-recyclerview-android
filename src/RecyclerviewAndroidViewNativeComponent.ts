import codegenNativeComponent from "react-native/Libraries/Utilities/codegenNativeComponent";
import type { HostComponent, ViewProps } from "react-native";
import type * as ReactNative from 'react-native';
import type { DirectEventHandler, Int32 } from "react-native/Libraries/Types/CodegenTypes";
import codegenNativeCommands from "react-native/Libraries/Utilities/codegenNativeCommands";
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

export type TopHeaderItemStyle = {
  titleColor:string;
  subTitleColor:string;
  linkTextColor:string;
  titleFontSize:number;
  subTitleFontSize:number;
  linkTextFontSize:number;
}
export enum TopHeaderTemplate {
  face="face",
  full="full"
}
export type TopHeaderItem={
  imageUri:string;
  title:string;
  subTitle:string;
  linkText:string;
  style:TopHeaderItemStyle;
  topHeaderTemplate:TopHeaderTemplate;
}



export type MediaHttpHeaders={[key:string]:string};


export type RecyclerProps={
  data:SectionDataSource[];
  headerStyle:SectionHeaderStyle;
  httpHeaders:MediaHttpHeaders;
  topHeaderItem:TopHeaderItem;
}

interface NativeProps extends ViewProps {
  color?: string;
  sectionHeaderStyle?: string;
  dataSourceString?: string;
  dataSource?: Array<Asset>;
  httpHeadersString?: string;
  recyclerPropString?:string;
  OnRefreshing: DirectEventHandler<any>;
  onLongPressed: DirectEventHandler<OnPageSelectedEventData>;
  onItemPressed: DirectEventHandler<OnPageSelectedEventData>;
  OnItemSelectStateChanged: DirectEventHandler<OnPageSelectedEventData>;
  onVisibleItemsChange: DirectEventHandler<any>
}


const RecyclerviewAndroidView = codegenNativeComponent<NativeProps>("GalleryListView") as HostComponent<NativeProps>;;
export default RecyclerviewAndroidView

type RecyclerviewAndroidViewType = ReactNative.HostComponent<NativeProps>;

export interface NativeCommands {
  toggleSelectionMode: (
    viewRef: React.ElementRef<RecyclerviewAndroidViewType>,
    selectionMode: boolean
  ) => void;
  onRefreshEnd: (
    viewRef: React.ElementRef<RecyclerviewAndroidViewType>,
    refreshing: boolean
  ) => void;
  updateDataSource: (
    viewRef: React.ElementRef<RecyclerviewAndroidViewType>,
    dataSource: string
  ) => void;
}


export const Commands: NativeCommands = codegenNativeCommands<NativeCommands>({
  supportedCommands: [
    'toggleSelectionMode',
    'onRefreshEnd',
    'updateDataSource'
  ],
});
//export const ZoomableImageView = codegenNativeComponent<NativeProps>("ZoomableImageView");
