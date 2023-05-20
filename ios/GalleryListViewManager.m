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
RCT_EXPORT_VIEW_PROPERTY(onLongPressed, RCTDirectEventBlock)
@end
