import codegenNativeComponent from "react-native/Libraries/Utilities/codegenNativeComponent";
import codegenNativeCommands from "react-native/Libraries/Utilities/codegenNativeCommands";
const RecyclerviewAndroidView = codegenNativeComponent("GalleryListView");
;
export default RecyclerviewAndroidView;
export const Commands = codegenNativeCommands({
  supportedCommands: ['toggleSelectionMode', 'onRefreshEnd', 'updateDataSource']
});
//export const ZoomableImageView = codegenNativeComponent<NativeProps>("ZoomableImageView");
//# sourceMappingURL=RecyclerviewAndroidViewNativeComponent.js.map