#ifdef RCT_NEW_ARCH_ENABLED
#import "RecyclerviewAndroidView.h"

#import <react/renderer/components/RNRecyclerviewAndroidViewSpec/ComponentDescriptors.h>
#import <react/renderer/components/RNRecyclerviewAndroidViewSpec/EventEmitters.h>
#import <react/renderer/components/RNRecyclerviewAndroidViewSpec/Props.h>
#import <react/renderer/components/RNRecyclerviewAndroidViewSpec/RCTComponentViewHelpers.h>

#import "RCTFabricComponentsPlugins.h"
#import "Utils.h"
using namespace facebook::react;

@interface RecyclerviewAndroidView () <RCTRecyclerviewAndroidViewViewProtocol>

@end

@implementation RecyclerviewAndroidView {
    UICollectionView * _view;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
    return concreteComponentDescriptorProvider<RecyclerviewAndroidViewComponentDescriptor>();
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if (self = [super initWithFrame:frame]) {
    static const auto defaultProps = std::make_shared<const RecyclerviewAndroidViewProps>();
    _props = defaultProps;

  UICollectionViewFlowLayout* flowLayout = [[UICollectionViewFlowLayout alloc] init];
  flowLayout.itemSize = CGSizeMake(100, 100);
  [flowLayout setScrollDirection:UICollectionViewScrollDirectionHorizontal];
  _view = [[UICollectionView alloc] initWithFrame:frame collectionViewLayout:flowLayout];
  [_view registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:@"cell"];
  
    
    self.contentView = _view;
  }

  return self;
}

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
    const auto &oldViewProps = *std::static_pointer_cast<RecyclerviewAndroidViewProps const>(_props);
    const auto &newViewProps = *std::static_pointer_cast<RecyclerviewAndroidViewProps const>(props);

    if (oldViewProps.color != newViewProps.color) {
        NSString * colorToConvert = [[NSString alloc] initWithUTF8String: newViewProps.color.c_str()];
        [_view setBackgroundColor: [Utils hexStringToColor:colorToConvert]];
    }
    if(oldViewProps.dataSourceString!=newViewProps.dataSourceString){
        NSLog(@"Your name is %lu", newViewProps.dataSourceString.length());
         
    }

    [super updateProps:props oldProps:oldProps];
}

Class<RCTComponentViewProtocol> RecyclerviewAndroidViewCls(void)
{
    return RecyclerviewAndroidView.class;
}

@end
#endif
