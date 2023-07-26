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

export type MediaHttpHeaders={[key:string]:string};


interface NativeProps extends ViewProps {
  color?: string;
  sectionHeaderStyle?: string;
  dataSourceString?: string;
  dataSource?: Array<Asset>;
  httpHeadersString?: string;
  onLongPressed: DirectEventHandler<OnPageSelectedEventData>;
  onItemPressed: DirectEventHandler<OnPageSelectedEventData>;
  OnItemSelectStateChanged: DirectEventHandler<OnPageSelectedEventData>;
}


const RecyclerviewAndroidView = codegenNativeComponent<NativeProps>("GalleryListView") as HostComponent<NativeProps>;;
export default RecyclerviewAndroidView

type RecyclerviewAndroidViewType = ReactNative.HostComponent<NativeProps>;

export interface NativeCommands {
  toggleSelectionMode: (
    viewRef: React.ElementRef<RecyclerviewAndroidViewType>,
    selectionMode: boolean
  ) => void;
}


export const Commands: NativeCommands = codegenNativeCommands<NativeCommands>({
  supportedCommands: [
    'toggleSelectionMode' 
  ],
});
//export const ZoomableImageView = codegenNativeComponent<NativeProps>("ZoomableImageView");
