"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.default = exports.Commands = void 0;
var _codegenNativeComponent = _interopRequireDefault(require("react-native/Libraries/Utilities/codegenNativeComponent"));
var _codegenNativeCommands = _interopRequireDefault(require("react-native/Libraries/Utilities/codegenNativeCommands"));
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
const RecyclerviewAndroidView = (0, _codegenNativeComponent.default)("GalleryListView");
;
var _default = RecyclerviewAndroidView;
exports.default = _default;
const Commands = (0, _codegenNativeCommands.default)({
  supportedCommands: ['toggleSelectionMode', 'onRefreshEnd', 'updateDataSource']
});
//export const ZoomableImageView = codegenNativeComponent<NativeProps>("ZoomableImageView");
exports.Commands = Commands;
//# sourceMappingURL=RecyclerviewAndroidViewNativeComponent.js.map