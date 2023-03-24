// This guard prevent this file to be compiled in the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTViewComponentView.h>
#import <UIKit/UIKit.h>

#ifndef RecyclerviewAndroidViewNativeComponent_h
#define RecyclerviewAndroidViewNativeComponent_h

NS_ASSUME_NONNULL_BEGIN

@interface RecyclerviewAndroidView : RCTViewComponentView
@end

NS_ASSUME_NONNULL_END

#endif /* RecyclerviewAndroidViewNativeComponent_h */
#endif /* RCT_NEW_ARCH_ENABLED */
