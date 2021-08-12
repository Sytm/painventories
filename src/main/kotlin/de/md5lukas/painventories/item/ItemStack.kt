package de.md5lukas.painventories.item

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*

/**
 * Create an [ItemStack] using the provided [Material] and initialize it
 *
 * @param type The Material to use
 * @param init The initializer for the ItemStack
 */
inline fun itemStack(type: Material, init: ItemStack.() -> Unit) = ItemStack(type).apply(init)

/**
 * Applies the initializer on the current [ItemMeta] if present and then updates the ItemMeta in the ItemStack
 *
 * @receiver The ItemStack to modify the ItemMeta of
 * @param init The initializer for the ItemMeta
 */
inline fun ItemStack.meta(init: ItemMeta.() -> Unit) {
    this.itemMeta = this.itemMeta?.apply(init)
}

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [BannerMeta]
 *
 * @receiver The ItemStack to modify the BannerMeta of
 * @param init The initializer for the BannerMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type BannerMeta
 */
inline fun ItemStack.bannerMeta(init: BannerMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [BlockStateMeta]
 *
 * @receiver The ItemStack to modify the BlockStateMeta of
 * @param init The initializer for the BlockStateMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type BlockStateMeta
 */
inline fun ItemStack.blockStateMeta(init: BlockStateMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [BookMeta]
 *
 * @receiver The ItemStack to modify the BookMeta of
 * @param init The initializer for the BookMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type BookMeta
 */
inline fun ItemStack.bookMeta(init: BookMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as an [EnchantmentStorageMeta]
 *
 * @receiver The ItemStack to modify the EnchantmentStorageMeta of
 * @param init The initializer for the EnchantmentStorageMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type EnchantmentStorageMeta
 */
inline fun ItemStack.enchantmentStorageMeta(init: EnchantmentStorageMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [FireworkEffectMeta]
 *
 * @receiver The ItemStack to modify the FireworkEffectMeta of
 * @param init The initializer for the FireworkEffectMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type FireworkEffectMeta
 */
inline fun ItemStack.fireworkEffectMeta(init: FireworkEffectMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [FireworkMeta]
 *
 * @receiver The ItemStack to modify the FireworkMeta of
 * @param init The initializer for the FireworkMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type FireworkMeta
 */
inline fun ItemStack.fireworkMeta(init: FireworkMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [KnowledgeBookMeta]
 *
 * @receiver The ItemStack to modify the KnowledgeBookMeta of
 * @param init The initializer for the KnowledgeBookMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type KnowledgeBookMeta
 */
inline fun ItemStack.knowledgeBookMeta(init: KnowledgeBookMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [LeatherArmorMeta]
 *
 * @receiver The ItemStack to modify the LeatherArmorMeta of
 * @param init The initializer for the LeatherArmorMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type LeatherArmorMeta
 */
inline fun ItemStack.leatherArmorMeta(init: LeatherArmorMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [MapMeta]
 *
 * @receiver The ItemStack to modify the MapMeta of
 * @param init The initializer for the MapMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type MapMeta
 */
inline fun ItemStack.mapMeta(init: MapMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [PotionMeta]
 *
 * @receiver The ItemStack to modify the PotionMeta of
 * @param init The initializer for the PotionMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type PotionMeta
 */
inline fun ItemStack.potionMeta(init: PotionMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [SkullMeta]
 *
 * @receiver The ItemStack to modify the SkullMeta of
 * @param init The initializer for the SkullMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type SkullMeta
 */
inline fun ItemStack.skullMeta(init: SkullMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [SpawnEggMeta]
 *
 * @receiver The ItemStack to modify the SpawnEggMeta of
 * @param init The initializer for the SpawnEggMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type SpawnEggMeta
 */
inline fun ItemStack.spawnEggMeta(init: SpawnEggMeta.() -> Unit) = genericMeta(init)

/**
 * Tries to apply the initializer on the current [ItemMeta] as a [TropicalFishBucketMeta]
 *
 * @receiver The ItemStack to modify the TropicalFishBucketMeta of
 * @param init The initializer for the TropicalFishBucketMeta
 * @throws IllegalArgumentException If the ItemMeta of the ItemStack is not of the type TropicalFishBucketMeta
 */
inline fun ItemStack.tropicalFishBucketMeta(init: TropicalFishBucketMeta.() -> Unit) = genericMeta(init)

@PublishedApi
internal inline fun <reified T : ItemMeta> ItemStack.genericMeta(init: T.() -> Unit) {
    val meta = this.itemMeta
    if (meta is T) {
        this.itemMeta = meta.apply(init)
    } else {
        val className = T::class.simpleName
        throw IllegalArgumentException("Trying to modify the ItemMeta as $className, but it is not of type $className")
    }
}