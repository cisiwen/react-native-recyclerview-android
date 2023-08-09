//
//  GalleryListView.m
//  react-native-recyclerview-android
//
//  Created by wweng on 20/5/2023.
//

#import <Foundation/Foundation.h>
#import "React/RCTViewManager.h"
@interface RCT_EXTERN_MODULE(GalleryListViewManager, RCTViewManager)
RCT_EXPORT_VIEW_PROPERTY(dataSourceString, NSString)
RCT_EXPORT_VIEW_PROPERTY(sectionHeaderStyle, NSString)
RCT_EXPORT_VIEW_PROPERTY(recyclerPropString, NSString)
RCT_EXPORT_VIEW_PROPERTY(onLongPressed, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onItemPressed, RCTDirectEventBlock)
RCT_EXTERN_METHOD(toggleSelectionMode
                  : (nonnull NSNumber *)reactTag selectionMode
                  : (nonnull BOOL *)selectionMode)
@end

@interface RCT_EXTERN_MODULE(ZoomableImageViewManager, RCTViewManager)
@end
